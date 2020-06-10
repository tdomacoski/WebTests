const q_times = [0,0,0,0,0,0,0,0,0,0,0];
let sum = "";

//temos 12 posições, pois o maior número poderá ser 12 (6+6).
// a posição do número vai guardar quantas vezes ele saiu!
// sendo (numero-1) a referencia, pois a lista inicia em zero...
var numbers =[0,0,0,0,0,0,0,0,0,0,0,0];
function sumOfTheTwoDice(){
    for(let i =1; i <= 100; i++){
       var total = (randomNumbers(1,6) + randomNumbers(1,6));
       // adicionamos +1 na posição
       numbers[total-1] = (numbers[total-1] + 1);
    }
    numbers.forEach(function (item, indice, array) {
      console.log((indice+1)+' saiu '+item+' vezes..');
    });
}

function rollOfDices(){
    for(let i = 1; i <= 1000; i++){
        dice1 = randomNumbers(1,6);
        //console.log("dado 1 saiu o número:" + dice1);
        dice2 = randomNumbers(1,6);
         //console.log("dado 2 saiu o número:" + dice2);
        sum =+ dice1 + dice2;
         //console.log("A soma dos dados é de: " + sum);

        switch(sum){
            case 2:
                q_times.push[0];
                break;
            case 3:
                q_times.push[1];
                break;
            case 4:
                q_times.push[2];
                break;
            case 5:
                q_times.push[3];
                break;
            case 6:
                q_times.push[4];
                break;
            case 7:
                q_times.push[5];
                break;
            case 8:
                q_times.push[6];
                break;
            case 9:
                q_times.push[7];
                break;
            case 10:
                q_times.push[8];
                break;
            case 11:
                q_times.push[9];
                break;
            case 12:
                q_times.push[10];
                break;             
        }
    }
    return sum;
}

function randomNumbers(min, max){
    min = Math.ceil(min); 
    max = Math.floor(max); 
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
/* 
A função Math.ceil(x) retorna o menor número inteiro maior ou igual a "x".
A função Math.floor(x) retorna o menor número inteiro dentre o número "x".
A função Math.random() retorna um número pseudo-aleatório no intervalo [0, 1[, ou seja, de 0 (inclusivo) até, mas não incluindo, 1 (exclusivo), que depois você pode dimensionar para um intervalo desejado.
*/