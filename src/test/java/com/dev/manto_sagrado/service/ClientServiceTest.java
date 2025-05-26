package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.client.dto.ClientLoginResponseDTO;
import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.exception.InvalidClientNameException;
import com.dev.manto_sagrado.exception.InvalidCpfException;
import com.dev.manto_sagrado.exception.InvalidEmailException;
import com.dev.manto_sagrado.repository.AddressRepository;
import com.dev.manto_sagrado.repository.ClientRepository;
import com.dev.manto_sagrado.utils.CpfValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    private MockedStatic<CpfValidator> cpfValidatorMock;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

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
    @DisplayName("Deve lançar exceção ao tentar salvar cliente com CPF inválido")
    void testSave_WithInvalidCpf_ShouldThrowException() {
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("12345678900")
                .password("senha123")
                .build();

        cpfValidatorMock = mockStatic(CpfValidator.class);
        cpfValidatorMock.when(() -> CpfValidator.isValid("21365498722")).thenReturn(false);

        assertThatThrownBy(() -> clientService.save(client))
                .isInstanceOf(InvalidCpfException.class)
                .hasMessage("CPF inválido. Por favor, verifique os dígitos");

        verify(clientRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() ao tentar salvar cliente com e-mail já cadastrado")
    void testSave_WithDuplicateEmail_ShouldReturnEmptyOptional() {
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("89489675091")
                .password("senha123")
                .build();

        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.save(client);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar cliente com dados válidos e retornar Optional com cliente")
    void testSave_WithValidData_ShouldReturnClient() {
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("89489675091")
                .password("senha123")
                .build();

        cpfValidatorMock = mockStatic(CpfValidator.class);
        cpfValidatorMock.when(() -> CpfValidator.isValid("12345678900")).thenReturn(true);

        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha123")).thenReturn("senhaCodificada");
        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Client> result = clientService.save(client);

        assertTrue(result.isPresent());
        assertEquals("senhaCodificada", result.get().getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar cliente com nome inválido")
    void testSave_WithInvalidName_ShouldThrowException() {
        Client client = Client.builder()
                .id(1L)
                .name("Jo") // nome inválido
                .email("jo@email.com")
                .cpf("89489675091")
                .password("senha123")
                .build();

        cpfValidatorMock = mockStatic(CpfValidator.class);
        cpfValidatorMock.when(() -> CpfValidator.isValid("12345678900")).thenReturn(true);

        assertThrows(InvalidClientNameException.class, () -> clientService.save(client));
    }

    @Test
    @DisplayName("Deve retornar DTO de login ao autenticar com credenciais corretas")
    void testLogin_WithCorrectCredentials_ShouldReturnDTO() {
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("89489675091")
                .password("senhaCodificada")
                .build();

        ClientRequestDTO requestDTO = new ClientRequestDTO(
                1L,
                "joao@email.com",
                "senha123",
                "João Silva",
                "89489675091",
                LocalDate.of(2000, 1, 1),
                null
        );

        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(client));
        when(passwordEncoder.matches("senha123", "senhaCodificada")).thenReturn(true);

        ClientLoginResponseDTO response = clientService.login(requestDTO);

        assertNotNull(response);
        assertEquals("joao@email.com", response.getEmail());
    }

    @Test
    @DisplayName("Deve retornar null ao tentar autenticar com senha incorreta")
    void testLogin_WithWrongPassword_ShouldReturnNull() {
        Client client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .cpf("89489675091")
                .password("senhaCodificada")
                .build();

        ClientRequestDTO requestDTO = new ClientRequestDTO(
                1L,
                "joao@email.com",
                "senhaErrada",
                "João Silva",
                "89489675091",
                LocalDate.of(2000, 1, 1),
                null
        );

        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(client));
        when(passwordEncoder.matches("senhaErrada", "senhaCodificada")).thenReturn(false);

        ClientLoginResponseDTO response = clientService.login(requestDTO);

        assertNull(response);
    }

    @Test
    @DisplayName("Deve retornar null ao tentar autenticar com e-mail inexistente")
    void testLogin_WithNonExistentEmail_ShouldReturnNull() {
        ClientRequestDTO requestDTO = new ClientRequestDTO(
                1L,
                "naoexiste@email.com",
                "senha123",
                "Fulano de Tal",
                "89489675091",
                LocalDate.of(1990, 1, 1),
                null
        );

        when(clientRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        ClientLoginResponseDTO response = clientService.login(requestDTO);

        assertNull(response);
    }
}