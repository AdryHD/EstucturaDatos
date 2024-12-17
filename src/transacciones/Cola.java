package transacciones;

import datos.Nodo;
import datos.Tiquete;

/**
 *
 * @author adryhd
 */
public class Cola {

    private Nodo prim, ult;
    private int cant = 0;

    public boolean esVacia() {
        if (prim == null) {
            return true;
        } else {
            return false;
        }
    }

    public int encola(Tiquete pDato) {
        Nodo nuevo = new Nodo(pDato);
        if (this.esVacia()) {
            prim = ult = nuevo;
        } else {
            ult.setSig(nuevo);
            ult = nuevo;
        }
        cant += 1;
        return 1;
    }

    public int atiende() {
        if (!this.esVacia()) {
            if (prim == ult) {
                prim = ult = null;
            } else {
                prim = prim.getSig();
            }
            cant -= 1;
            return 1;
        } else {
            return -1;//cola es vacia
        }
    }

    public boolean encontrar(int n) {
        if (this.esVacia()) {
            return false;
        } else {
            Nodo aux = prim;
            while (aux != null) {
                if (n == aux.getNumDato().getCajaAsignada()) {
                    return true;
                }
                aux = aux.getSig();
            }

            return false;
        }

    }

    public int eliminar(int n) {
        if (this.esVacia()) {
            return -1;//informar que la pila es vacia
        } else {
            if (prim.getNumDato().getId() == n) {
                if (prim == ult) {
                    prim = ult = null;
                } else {
                    prim = prim.getSig();
                }
                return 1;
            } else {
                Nodo aux = prim;
                while (aux.getSig() != null) {
                    if (aux.getSig().getNumDato().getId() == n) {
                        aux.setSig(aux.getSig().getSig());
                        return 1;
                    }
                    aux = aux.getSig();
                }
            }
            return -2;//no se encontro el elemento a eliminar
        }
    }

    public Tiquete extrae(int n) {
        if (this.esVacia()) {
            return null;
        } else {
            Tiquete extraido;
            if (prim.getNumDato().getId() == n) {
                extraido = prim.getNumDato();
                if (prim == ult) {
                    prim = ult = null;
                } else {
                    prim = prim.getSig();
                }
                return extraido;
            } else {
                Nodo aux = prim;
                while (aux.getSig() != null) {
                    if (aux.getSig().getNumDato().getId() == n) {
                        extraido = aux.getSig().getNumDato();
                        aux.setSig(aux.getSig().getSig());
                        return extraido;
                    }
                    aux = aux.getSig();
                }
                return null;
            }

        }
    }


    
    
        public Tiquete getNodo() {
        if (this.esVacia()) {
            return null; // retorna Null si la cola está vacía
        } else {
            Tiquete firstTiquete = prim.getNumDato(); // obtiene el primer tiquete de la cola
            if (prim == ult) {
                prim = ult = null; // si solo hay un tiquete, elimina la cola
            } else {
                prim = prim.getSig();  //mueve el apuntador al siquiente nodo
            }
            cant--;
            return firstTiquete; // retorna el primer tiquete de la cola
            }
        }
    

    public int getSize() {
        return cant;
    }

    @Override
    public String toString() {
        Nodo aux = prim;
        String r = "COLA\nCantidad de elementos: " + cant + "\n";
        if (!this.esVacia()) {
            while (aux != null) {
                r += aux + "\n";
                aux = aux.getSig();
            }
        } else {
            r += "vacia";
        }
        return r;
    }

}
