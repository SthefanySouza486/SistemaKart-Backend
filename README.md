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

```Bash
git clone https://github.com/SthefanySouza486/SistemaKart-Backend.git
cd kartapi-backend
```


**Configure o Banco de Dados:**

Crie um banco de dados PostgreSQL (ex: kart_club_db).

Abra o arquivo src/main/resources/application.properties.

Altere as seguintes linhas com suas credenciais:

```Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kart_club_db
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
```

Configure o JWT:

No mesmo application.properties, defina sua chave secreta do JWT (necessária para iniciar a aplicação):

```Properties
jwt.secret=SuaChaveSecretaMuitoLongaESeguraAqui123456
```

Execute a API:

```Bash
./mvnw spring-boot:run
```

A API estará rodando em http://localhost:8080.

**2. Front-end (Repositório Separado)**
Clone o repositório do front-end:

```Bash
git clone https://github.com/SeuUsuario/kart-frontend.git
cd kart-frontend
```

Instale as dependências:

```Bash
npm install
```

Conecte o Front ao Back:

Crie um arquivo .env (ou similar) na raiz do projeto front-end.

Adicione a URL da API do back-end:
```
REACT_APP_API_URL=http://localhost:8080
```
**Nota: O Back-end já está configurado (CorsConfig.java) para aceitar requisições do front-end.**

Execute o Front-end:

```Bash 
npm start
```
**Nota: Após executar o front-end, aparecera um link, aperte Ctrl e clique nesse link, assim abrirá o sistema**

## 📖 Manual do Usuário
Com ambos os servidores rodando, o sistema está pronto para uso.

**Fluxo de Gestão (Admin)**
**1.** Criar o Primeiro Gestor: (Passo de configuração única)

O sistema precisa de um GESTOR para começar. Execute o SQL abaixo diretamente no seu banco de dados para criar um.

(A senha é senha123, já criptografada com BCrypt):

```SQL
INSERT INTO gestor (nome, email, senha, tipo_usuario)
VALUES ('Admin Gestor', 'gestor@kartclub.com', '$2a$10$7b.bBy8M..g.Jg0.C.p65u8yv/2c.CyEcLz.4m.e.j.d9.T0c.7rW', 'GESTOR');
```

**2.** Login: 

<img width="940" height="428" alt="image" src="https://github.com/user-attachments/assets/fa6b767e-2791-4ea1-acc4-b52a6bc5b52a" />

Acesse a aplicação e faça login com as credenciais do gestor (gestor@kartclub.com / senha123).

**3.** Vizualizar todos os agendamentos: 

<img width="940" height="433" alt="image" src="https://github.com/user-attachments/assets/17ce0d32-3b53-4051-9acf-a28b4f71b641" />

Nesta aba, consegue-se visualizar todos os agendamentos que tem, seja qual for a situação do agendamento. 


**4.** Vizualizar os agendamentos pendentes: 

<img width="940" height="433" alt="image" src="https://github.com/user-attachments/assets/c13523fc-ddaf-42da-a520-81c459ffa10d" />

Nesta aba de pendentes, consegue-se visualizar todos os agendamentos, que ainda faltam concluir ou cancelar. 


**5.** Visualizar os agendamentos confirmados: 

<img width="940" height="414" alt="image" src="https://github.com/user-attachments/assets/cabf7bb7-bb65-4982-9101-aecd851547f5" />

Nesta aba de confirmados, consegue-se visualizar os arquivos que estão confirmados e pode-se conclui-los.


**6.** Visualizar os agendamentos concluidos: 

<img width="940" height="342" alt="image" src="https://github.com/user-attachments/assets/ef71ece7-b6a3-45f3-8372-307255aad647" />

Nesta aba de concluidos, o gestor consegue visualizar todos os agendamentos que foram concluidos. 


**7.** Visualizar os agendamentos cancelados: 

<img width="940" height="427" alt="image" src="https://github.com/user-attachments/assets/f97e3d71-611f-4c5f-af0b-c41c23077ca2" />

Nesta aba de cancelados, consegue-se visualizar todos os agendamentos que foram cancelados. 

**Fluxo de Cliente**
**1.** Cadastro do cliente: 

<img width="940" height="426" alt="image" src="https://github.com/user-attachments/assets/88ac94b8-7eb7-4bcd-9bda-9540e03043a8" />


Nesta aba de cadastro, os clientes fazem um simples cadastro para ter acesso a plataforma. 



**2.** Login: 

<img width="940" height="428" alt="image" src="https://github.com/user-attachments/assets/970aa6b4-0e7e-47bb-99d0-f5a050f503b4" />

Nesta aba de login, o cliente insere o email e a senha que colocou para fazer o cadastro. 


**3.** Agendar um horário: 

<img width="940" height="684" alt="image" src="https://github.com/user-attachments/assets/06be7efd-15b9-41d0-9af3-021d63850184" />


Nesta aba, o cliente ira agendar um horário, selecionando, a data, o horário e a quantidade de pessoas. 



**4.** Tela de pagamento: 

<img width="940" height="551" alt="image" src="https://github.com/user-attachments/assets/581ca5b7-f2b3-4314-aaef-6609012c3561" />


Nesta aba de pagamento, o cliente irá copiar o pix, enviar o comprovante no número que esta nas instruções e confirmar o pagamento. 



**5.** Meus agendamentos: 

<img width="940" height="358" alt="image" src="https://github.com/user-attachments/assets/7bfffec8-6fc3-4874-9d50-d46e4d1cf5bb" />

Nesta tela, o usuário consegue visualizar os agendamentos que tem, como o dia, a hora. 



**6.** Sobre: 

<img width="940" height="483" alt="image" src="https://github.com/user-attachments/assets/71d26261-aeea-4bb9-9704-9e46dd1f7bcc" />


Nesta tela, o usuário verá todas as regras para andar de kart. 



## 👩🏻‍💻👨🏻‍💻👨🏻‍💻 Autores: 
Este sistema foi desenvolvido por: Sthefany (https://github.com/SthefanySouza486), Leonardo (https://github.com/leocaua) e Pedro (https://github.com/PedroGuireli). 
