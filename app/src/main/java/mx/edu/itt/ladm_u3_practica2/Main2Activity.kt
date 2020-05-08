package mx.edu.itt.ladm_u3_practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var baseremota = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var extr = intent.extras
        var id =extr?.getString("id").toString()
        nombreM.setText(extr?.getString("nombre"))
        domicilioM.setText(extr?.getString("domicilio"))
        celularM.setText(extr?.getString("celular"))
        descripcionM.setText(extr?.getString("descripcion"))
        cantidadM.setText(extr?.getString("cantidad"))
        precioM.setText(extr?.getString("precio"))
        var ent=extr?.getString("entregado")

        entregadoM.isChecked=false
        if(ent.equals("True")){
            entregadoM.isChecked=true
        }

        Actualizar.setOnClickListener {
            AlertDialog.Builder(this).setMessage("¿Estas seguro que deseas Actualizarlo?")
                .setPositiveButton("ok"){d,r->

                    var entr ="False"
                    if(entregadoM.isChecked==true){entr="True"}
                   
                    var data  =  hashMapOf<String, Any>(
                        "nombre" to nombreM.text.toString(),
                        "domicilio" to domicilioM.text.toString(),
                        "celular" to celularM.text.toString(),
                        "pedido" to hashMapOf(
                            "descripcion" to  descripcionM.text.toString(),
                            "precio" to precioM.text.toString().toDouble(),
                            "cantidad" to  cantidadM.text.toString().toInt(),
                            "entregado" to entr

                        )

                    )

                    baseremota.collection("restaurante").document(id)
                        .update(data)
                        .addOnSuccessListener { msj("Se actualizó correctamente!")
                            finish()
                        }
                        .addOnFailureListener {msj("Eror:No se actualizó !") }

                }.show()
        }
        Eliminar.setOnClickListener {
            AlertDialog.Builder(this).setMessage("¿Estas seguro que deseas elimnarlo?")
                .setPositiveButton("ok"){d,r->
                    baseremota.collection("restaurante").document(id)
                        .delete()
                        .addOnSuccessListener { msj("Se eliminó correctamente!")
                            finish()
                        }
                        .addOnFailureListener {msj("Eror:No se eliminó !") }

                }.show()

        }


    }



    fun msj (i:String){
        Toast.makeText(this,i,Toast.LENGTH_LONG).show()
    }
}
