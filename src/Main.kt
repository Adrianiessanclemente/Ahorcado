fun main() {
    println("Seleccione una opción:")
    println("1. Jugar")
    println("2. Ver partida anterior")
    println("3. Salir")

//Bucle que gestiona el menú de opciones degún la entrada del Usuario
    while(true) {
        val entry= readln()
        if(entry == "1"){
            play()
        }else if(entry == "2"){
            loadLastGame()
        }else if(entry == "3"){
            return
        }else{
            println("Introduce un número válido")

        }
    }
}