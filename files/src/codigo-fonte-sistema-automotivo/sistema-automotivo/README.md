# Sistema Automotivo — Gestão de Estoque de Veículos

**UniFECAF | Análise e Desenvolvimento de Sistemas | Programação Orientada a Objetos**



Sobre o Projeto

Sistema CRUD completo para gerenciamento de estoque de veículos em concessionárias,
desenvolvido com Java, Spring Boot e MySQL, aplicando os princípios da POO.



##Estrutura de Pastas

```
sistema-automotivo/
├── pom.xml                          ← Dependências Maven
└── src/main/
    ├── java/com/unifecaf/automotivo/
    │   ├── SistemaAutomotivoApplication.java   ← Classe principal
    │   ├── model/
    │   │   ├── VeiculoBase.java     ← Classe abstrata (Herança/POO)
    │   │   ├── Veiculo.java         ← Entidade principal
    │   │   ├── Modelo.java          ← Entidade modelo
    │   │   ├── Marca.java           ← Entidade marca
    │   │   └── StatusVeiculo.java   ← Enum de status
    │   ├── repository/
    │   │   ├── VeiculoRepository.java
    │   │   ├── ModeloRepository.java
    │   │   └── MarcaRepository.java
    │   ├── service/
    │   │   ├── VeiculoService.java  ← Regras de negócio
    │   │   ├── ModeloService.java
    │   │   └── MarcaService.java
    │   ├── controller/
    │   │   ├── VeiculoController.java  ← Endpoints REST
    │   │   ├── ModeloController.java
    │   │   └── MarcaController.java
    │   └── exception/
    │       ├── RecursoNaoEncontradoException.java
    │       └── GlobalExceptionHandler.java
    └── resources/
        ├── application.properties   ← Configuração do banco
        └── schema.sql               ← Script DDL MySQL
```

---

## Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- MySQL 8.x rodando localmente

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/sistema-automotivo.git
cd sistema-automotivo

# 2. Execute o script SQL no MySQL
mysql -u root -p < src/main/resources/schema.sql

# 3. Configure suas credenciais em:
#    src/main/resources/application.properties
#    (altere spring.datasource.password)

# 4. Compile e execute
mvn spring-boot:run
```

### Acesse a API
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:**   http://localhost:8080/api-docs

---

## Endpoints Principais

### Veículos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/veiculos` | Lista todos |
| GET | `/api/veiculos/{id}` | Busca por ID |
| GET | `/api/veiculos/filtrar` | Filtro combinado |
| POST | `/api/veiculos/modelo/{modeloId}` | Cadastra novo |
| PUT | `/api/veiculos/{id}` | Atualiza |
| DELETE | `/api/veiculos/{id}` | Remove |

### Parâmetros do Filtro (`/api/veiculos/filtrar`)
| Parâmetro | Tipo | Exemplo |
|-----------|------|---------|
| `marca` | String | `Toyota` |
| `modelo` | String | `Corolla` |
| `ano` | Integer | `2022` |
| `status` | Enum | `DISPONIVEL` |
| `precoMin` | Decimal | `50000` |
| `precoMax` | Decimal | `150000` |

**Exemplo de chamada:**
```
GET /api/veiculos/filtrar?marca=Toyota&status=DISPONIVEL&precoMax=150000
```

---

## Arquitetura

```
Cliente (Postman / Swagger)
        │
        ▼
  [ Controller ]   ← Recebe requisição HTTP, valida entrada
        │
        ▼
  [   Service   ]  ← Regras de negócio, validações de domínio
        │
        ▼
  [ Repository  ]  ← Acesso ao banco de dados via JPA
        │
        ▼
  [   MySQL DB  ]  ← Persistência dos dados
```

---

## Conceitos de POO Aplicados

| Conceito | Onde foi aplicado |
|----------|-------------------|
| **Classes** | Marca, Modelo, Veiculo, Services, Controllers |
| **Encapsulamento** | Atributos `private` + Lombok `@Getter/@Setter` |
| **Herança** | `Veiculo extends VeiculoBase` |
| **Abstração** | Classe abstrata `VeiculoBase`, interfaces Repository |
| **Polimorfismo** | Método `getTipoVeiculo()` sobrescrito em subclasses |

---

## Tecnologias

- **Java 17** (LTS)
- **Spring Boot 3.2** (Web, Data JPA, Validation)
- **MySQL 8** + **Hibernate**
- **Lombok** (redução de boilerplate)
- **Springdoc OpenAPI** (Swagger UI automático)
- **Maven** (gerenciamento de dependências)
