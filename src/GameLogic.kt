import java.io.File


//Función que se encarga de seleccionar una palabra aleatoria del diccionario.
fun generateRandomWord(): String {

    val diccionario = mapOf(
     "perro" to   "perro"
     ,"gato" to "gato",
     "loro" to "loro" ,
     "gallina" to "gallina",
     "vaca" to "vaca",
     "burro" to  "burro",
     "caballo" to  "caballo",
     "tigre" to "tigre",
     "lubina" to  "lubina",
     "merluza" to "merluza",
     "rana" to "rana",
     "gallo" to  "gallo")



    val secretWord = diccionario.keys.random()




    return secretWord
}

//Función que gestiona la entrada del Usuario durante la partida
fun userEntry(): String {
    while (true) {

        println("Averigua el animal")

        val entry = readln()

        if (entry.length in 4..7 && entry.all { it in 'a'..'z' } ) {
            return entry
        } else {
            println("Entrada inválida. Asegúrate de que el nombre tenga entre 4 y 7  letras")
        }
    }
}


//Función que comprueba la entrada del usuario y la compara con el número secreto.
//Si la cifra no esta en el número secreto se vera rojo, si esta en el número pero en la posción
//incorrecta se vera en amarrilo y si esta en el numero y en la posicion correcta sera verde.
fun checkWords(secretWord:String, attemp:String):Pair<Int,Int>  {

    var correctLettersAndPosition = 0
    var correctLetter = 0
    val secretList = secretWord.toList()
    val attempList = attemp.toList()


    val checked = MutableList(secretWord.length){false}
    val colors = MutableList(attemp.length){""}

    val limit = minOf(secretList.size, attempList.size)



    // Contar los aciertos
    for (i in 0 until limit) {
        if (attempList[i] == secretList[i]) {
            correctLettersAndPosition++
            colors[i] = "\u001B[32m"
            checked[i]=true

        }
    }


    // Contar las coincidencias
    for (i in 0 until limit) {

        if (attempList[i] != secretList[i] && secretList.contains(attempList[i])  ) {


            correctLetter++
            colors[i] = "\u001B[33m"
            checked[i]=true
        }


    }

    for(i in limit until attempList.size){

        if (secretList.contains(attempList[i])) {
            colors[i] = "\u001B[33m"
            correctLetter++

        }else{
            colors[i] = "\u001B[31m"
        }
    }


    for(i in attempList.indices){
        if(i<secretList.size){

        if(!checked[i]){
            colors[i] = "\u001B[31m"
        }
        }else {
        continue
        }
    }

    //Imprime el número del User con los colores adecuados
    for(i in attempList.indices){
        print("${colors[i]}${attempList[i]}\u001B[0m")
    }

    println()
    return Pair(correctLettersAndPosition, correctLetter)


}

//Función que incia el juego
fun play() {

    var rm = ReproductorMidi("pugnodollari.mid")

    println("Cargando...")

    Thread.sleep(8000)




    val secretWord = generateRandomWord()
    var attemps = 1
    val maxAattepms = 7

    println("Bienvenido al juego del Ahorcado!")
    println("La palabara secreta es un animal de entre 4 y 7 letras.")

    while (attemps < maxAattepms) {
        val attemp = userEntry()
        val (correctLettersAndPosition, correctLetter) = checkWords(secretWord, attemp)

        attemps++

        println("Intento #$attemps: $attemp")


        if (correctLettersAndPosition == secretWord.length) {
            println("¡Felicidades! Adivinaste el número secreto: $secretWord")
            println("Seleccione una opción:")
            println("1. Jugar")
            println("2. Ver partida anterior")
            println("3. Salir")
            break
        }else{
            DibujoAhorcado.dibujar(attemps)
        }

        if (attemps == maxAattepms) {
            println("¡Lo siento! Has agotado tus intentos. La palabra: $secretWord")
            println("Seleccione una opción:")
            println("1. Jugar")
            println("2. Ver partida anterior")
            println("3. Salir")

             rm.cerrar()
            break
        }
    }

    // Guardar el último intento en un archivo
    saveLastGame(secretWord, attemps)
}


//Función encargada de guardar los datos de la última partida
fun saveLastGame(secretWord: String, attemps: Int) {
    val file = File("ultima_jugada.txt")
    file.writeText("Número secreto: $secretWord\nIntentos: $attemps\n")
}

//Función encargada de cargar los datos de la última partida
fun loadLastGame(){
    val file = File("ultima_jugada.txt")

    if(file.exists()){
        val game = file.readText()

        println(game)
        println()
        println("Seleccione una opción:")
        println("1. Jugar")
        println("2. Ver partida anterior")
        println("3. Salir")



    }else{
        println("No hay partida anterior guardada.")

        println()
        println("Seleccione una opción:")
        println("1. Jugar")
        println("2. Ver partida anterior")
        println("3. Salir")
    }
}
