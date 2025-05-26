package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.userAdmin.Enum.Group;
import com.dev.manto_sagrado.domain.userAdmin.Enum.Status;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserAdminRequestDTO;
import com.dev.manto_sagrado.domain.userAdmin.dto.UserLoginResponseDTO;
import com.dev.manto_sagrado.domain.userAdmin.entity.UserAdmin;
import com.dev.manto_sagrado.exception.InvalidCpfException;
import com.dev.manto_sagrado.exception.InvalidEmailException;
import com.dev.manto_sagrado.exception.UserDeactivatedException;
import com.dev.manto_sagrado.infrastructure.utils.CpfValidator;
import com.dev.manto_sagrado.repository.UserAdminRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserAdminServiceTest {

    private MockedStatic<CpfValidator> cpfValidatorMock;

    @Mock
    private UserAdminRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserAdminService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        if (cpfValidatorMock != null) {
            cpfValidatorMock.close();
        }
    }

    @Test
    @DisplayName("Deve salvar o usuário com sucesso quando CPF é válido e e-mail não está em uso")
    void shouldSaveUserWhenEmailIsNotTakenAndCpfIsValid() {
        UserAdmin user = new UserAdmin();
        user.setName("Rodrigo Garro");
        user.setEmail("rodrigogarro@gmail.com");
        user.setPassword("123");
        user.setCpf("89489675091");
        user.setUserGroup(Group.ADMIN);
        user.setStatus(Status.ATIVADO);

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(user.getPassword())).thenReturn("encodedPassword");
        cpfValidatorMock = mockStatic(CpfValidator.class);
        cpfValidatorMock.when(() -> CpfValidator.isValid(anyString())).thenReturn(true);

        boolean result = service.save(user);

        assertThat(result).isTrue();
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Não deve salvar usuário se e-mail já estiver em uso")
    void shouldNotSaveUserWhenEmailIsTaken() {
        UserAdmin user = new UserAdmin();
        user.setEmail("admin@teste.com");

        when(repository.findByEmail("admin@teste.com")).thenReturn(Optional.of(new UserAdmin()));

        assertThatThrownBy(() -> service.save(user))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("O e-mail informado já está em uso. Por favor, utilize outro");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CPF for inválido")
    @Execution(ExecutionMode.SAME_THREAD)
    void shouldThrowExceptionWhenCpfIsInvalid() {
        UserAdmin user = new UserAdmin();
        user.setEmail("admin@teste.com");
        user.setCpf("00000000000");

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        cpfValidatorMock = mockStatic(CpfValidator.class);
        cpfValidatorMock.when(() -> CpfValidator.isValid(anyString())).thenReturn(false);

        assertThatThrownBy(() -> service.save(user))
                .isInstanceOf(InvalidCpfException.class)
                .hasMessage("CPF inválido. Por favor, verifique os dígitos");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve realizar login com sucesso quando e-mail e senha estiverem corretos e usuário estiver ativo")
    void shouldLoginSuccessfully() {
        UserAdminRequestDTO request = new UserAdminRequestDTO(0L, null, "admin@teste.com", "123456", null, null, null);
        UserAdmin user = new UserAdmin();
        user.setEmail("admin@teste.com");
        user.setPassword("encodedPassword");
        user.setStatus(Status.ATIVADO);

        when(repository.findByEmail("admin@teste.com")).thenReturn(Optional.of(user));
        when(encoder.matches("123456", "encodedPassword")).thenReturn(true);

        UserLoginResponseDTO response = service.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("admin@teste.com");
    }

    @Test
    @DisplayName("Deve retornar null se o e-mail não existir")
    void shouldReturnNullWhenEmailNotFound() {
        UserAdminRequestDTO request = new UserAdminRequestDTO(0L, null, "inexistente@teste.com", "123456", null, null, null);

        when(repository.findByEmail("inexistente@teste.com")).thenReturn(Optional.empty());

        UserLoginResponseDTO response = service.login(request);

        assertThat(response).isNull();
    }

    @Test
    @DisplayName("Deve lançar exceção se o usuário estiver desativado")
    void shouldThrowExceptionWhenUserIsDeactivated() {
        UserAdminRequestDTO request = new UserAdminRequestDTO(0L, null, "admin@teste.com", "123456", null, null, null);
        UserAdmin user = new UserAdmin();
        user.setEmail("admin@teste.com");
        user.setPassword("encodedPassword");
        user.setStatus(Status.DESATIVADO);

        when(repository.findByEmail("admin@teste.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(UserDeactivatedException.class)
                .hasMessage("Usuário está desativado e não pode fazer login.");
    }

    @Test
    @DisplayName("Deve retornar null se a senha estiver incorreta")
    void shouldReturnNullWhenPasswordIsIncorrect() {
        UserAdminRequestDTO request = new UserAdminRequestDTO(0L, null, "admin@teste.com", "senhaErrada", null, null, null);
        UserAdmin user = new UserAdmin();
        user.setEmail("admin@teste.com");
        user.setPassword("encodedPassword");
        user.setStatus(Status.ATIVADO);

        when(repository.findByEmail("admin@teste.com")).thenReturn(Optional.of(user));
        when(encoder.matches("senhaErrada", "encodedPassword")).thenReturn(false);

        UserLoginResponseDTO response = service.login(request);

        assertThat(response).isNull();
    }
}