# 🏎️ Kart Club - Sistema de Agendamento (kartapi)
Este repositório contém o Back-end (API) do Sistema de Agendamento do Kart Club. A API é responsável por toda a lógica de negócio, gerenciamento de usuários, regras de agendamento e processamento de pagamentos.

O projeto completo é desacoplado, e o Front-end (que consome esta API) pode ser encontrado no seguinte link https://github.com/SthefanySouza486/SistemaKart-Frontend.git.

## 🎯 O Problema
O processo de agendamento do clube era totalmente manual, lento e caro, sendo centralizado em um funcionário que recebia pedidos via WhatsApp. Isso gerava longas filas de espera, erros humanos na verificação de horários e prejuízo com reservas não pagas (no-shows).

## ✨ A Solução
Esta API (kartapi) resolve o problema criando um sistema de autoatendimento robusto e seguro. Ela automatiza 100% do processo, permitindo que clientes agendem 24/7 sem intervenção humana.

Principais Funcionalidades
Segurança: Autenticação e autorização via JWT (JSON Web Tokens).

Controle de Acesso: Sistema baseado em papéis (Roles) que diferencia CLIENTE de GESTOR.

Regras de Negócio: Validação automática e instantânea de regras complexas:

Horários Rígidos: Rejeita agendamentos fora da grade permitida (ex: dias de semana apenas 19:30).

Capacidade de Karts: Verifica se há karts DISPONIVEL suficientes para o número de participantes.

Sistema Anti-Conflito: Impede agendamentos "colados", garantindo um intervalo de tempo seguro entre as corridas.

Fluxo de Pagamento: Garante a reserva da vaga somente após o pagamento, alterando o status de PENDENTE para CONFIRMADO.

Painel de Gestão: Endpoints (/admin) protegidos para que o GESTOR possa administrar a frota de karts (ex: colocar em MANUTENCAO).

## 🛠️ Tecnologias Utilizadas (Stack)
- Java 17+

- Spring Boot

- Spring Security (com JWT)

- Spring Data JPA (Hibernate)

- PostgreSQL

- Maven

## 🔧 Instalação e Configuração
Para executar o projeto completo, você precisará configurar e executar o Back-end e o Front-end separadamente.

**1. Back-end (Este Repositório)**
Clone este repositório:

git clone [https://github.com/SeuUsuario/kartapi-backend.git](https://github.com/SthefanySouza486/SistemaKart-Backend.git)
cd kartapi-backend


**Configure o Banco de Dados:**

Crie um banco de dados PostgreSQL (ex: kart_club_db).

Abra o arquivo src/main/resources/application.properties.

Altere as seguintes linhas com suas credenciais:

Properties

spring.datasource.url=jdbc:postgresql://localhost:5432/kart_club_db
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
Configure o JWT:

No mesmo application.properties, defina sua chave secreta do JWT (necessária para iniciar a aplicação):

jwt.secret=SuaChaveSecretaMuitoLongaESeguraAqui123456

Execute a API:

./mvnw spring-boot:run

A API estará rodando em http://localhost:8080.

**2. Front-end (Repositório Separado)**
Clone o repositório do front-end:

git clone [https://github.com/SeuUsuario/kart-frontend.git](https://github.com/SthefanySouza486/SistemaKart-Frontend.git)
cd kart-frontend

Instale as dependências:

npm install

Conecte o Front ao Back:

Crie um arquivo .env (ou similar) na raiz do projeto front-end.

Adicione a URL da API do back-end:

REACT_APP_API_URL=http://localhost:8080
**Nota: O Back-end já está configurado (CorsConfig.java) para aceitar requisições do front-end.**

Execute o Front-end:

npm start

**Nota: Após executar o front-end, aparecera um link, aperte Ctrl e clique nesse link, assim abrirá o sistema**
