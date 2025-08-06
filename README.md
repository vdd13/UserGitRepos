Po uruchomieniu aplikacji wywołanie adresu

http://localhost:8080/repos/user

gdzie 'user' jest wyszukiwanym użytkownikiem github, np: 

http://localhost:8080/repos/vdd13

spowoduje że otrzymamy w odpowiedzi listę z oczekiwanymi informacjami - repozytorium, branch, owner, sha. 


Jeżeli wywołamy adres dla użytkownika który nie występuje w github, np: vdd131111 -

http://localhost:8080/repos/vdd131111

otrzymamy w odpowiedzi informację 'status:404' oraz 'message: user: vdd131111 not found'
