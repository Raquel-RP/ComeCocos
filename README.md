# ComeCocos
Práctica 8 de las asignatura Complementos de Programación

PROBLEMAS Y AVANCES MOVIMIENTO FANTASMAS:
- Avance: se mueven los dos que tienen movimiento más sencillo. Se supone que lo hacen solo a las posibles ya.
- Poblemas: van muy rápido. Llegados a un punto no reconocen bien qué direcciones tiene que coger y terminan parando.
- Implementar: al reiniciar el juego deben de reiniciarse también los fantasmas. Movimiento de los otros dos.



Falta por hacer: por orden de prioridad

- HECHO -> Botón start empieza un juego nuevo pero en una ventana nueva, corregir.
  revisar como hacer el método **terminar()** para que la hebra termine
- HECHO -> Botón resume no funciona. Ver cómo hacerlo en el método run() de TareaAnimarPersonajes, con el método synchronized() y wait()
- HECHO -> Botón salir del juego
- HECHO -> Puntuación que salga en pantalla actualizándose cada vez que se come un coco
- HECHO -> Terminar juego al comerse todos los cocos, falta implementar bien la salida del juego
- Implementar movimiento de fantasmas, ya están dibujados e inicializados
- HECHO -> Si fantasma te come fin del juego 
- HECHO -> Vidas del comecocos
- Puntuación final que depende del tiempo que hayas tardado y de los cocos comidos
