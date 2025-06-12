# 💸 Finance

## Alunos
- Luigi Almeida
- Rafael Azevedo
- Sophya Ribeiro

---

## Visão Geral do App  
**Finance** é um aplicativo mobile de controle financeiro pessoal voltado para usuários que desejam acompanhar seus gastos e ganhos de forma prática e segura. Com ele, o usuário pode cadastrar contas, registrar transações (entradas e saídas), visualizar o saldo atualizado e consultar um histórico completo das movimentações. O acesso ao sistema é feito por login com senha protegida por hash, e cada usuário possui um ambiente exclusivo. O aplicativo prioriza usabilidade com interface simples e objetiva, além de aplicar práticas básicas de segurança como hash de senha e autenticação por login. Futuramente, pode incluir suporte a recursos de acessibilidade como alto contraste e descrições de imagem.


---

## Papéis e Usuários  
- **Usuário Comum**  
  - Pode se cadastrar, fazer login e acessar seu próprio ambiente financeiro.  
  - Cada usuário tem sua própria base de contas e transações.  
  - O acesso é restrito por autenticação.  
  - A foto de perfil é salva no cadastro.

---

## Requisitos Funcionais

#### Autenticação
- RF01: O sistema deve permitir o cadastro de usuários com nome, e-mail, senha e foto.
- RF02: O sistema deve validar o login com e-mail e senha (hash).
- RF03: O sistema deve impedir o acesso de usuários não autenticados.

#### Contas e Transações
- RF04: O sistema deve permitir o cadastro de contas (ex: carteira, banco).
- RF05: O sistema deve permitir o cadastro de transações vinculadas a uma conta, podendo ser entrada ou saída.
- RF06: O sistema deve calcular automaticamente o saldo das contas.
- RF07: O sistema deve exibir uma lista de transações filtradas por data e categoria.
- RF08: O sistema deve permitir editar e excluir contas e transações.

#### Validações e Tratamento de Erros
- RF09: O sistema deve impedir o envio de formulários com campos obrigatórios vazios.
- RF10: O sistema deve impedir entrada de texto em campos numéricos.
- RF11: O sistema deve tratar erros como divisão por zero (se houver cálculo de médias ou proporções no futuro).

