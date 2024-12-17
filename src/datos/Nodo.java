/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import datos.Tiquete;

/**
 *
 * @author adryhd
 */
public class Nodo {
    private Tiquete numDato;//El elemento que contiene la informacion en el nodo
    private Nodo sig;//Dir memoria donde se encuentra el siguiente nodo

    public Nodo() {
    }
    
    public Nodo(Tiquete numDato) {
        this.numDato = numDato;
    }

    public Tiquete getNumDato() {
        return numDato;
    }

    public void setNumDato(Tiquete numDato) {
        this.numDato = numDato;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return "Nodo{" + "numDato=" + numDato + '}';
    }
}
