package se.appshack.android.refactoring.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_firebase_list.view.*
import se.appshack.android.refactoring.Activities.GameActivity
import se.appshack.android.refactoring.Activities.PokemonDetailsActivity
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.NamedResponseModel
import se.appshack.android.refactoring.R


class FirebasePokemonAdapter (var context: Context, var pokeFire : List<PokemonFirebaseClass>) : RecyclerView.Adapter<FirebasePokemonAdapter.PokemonFirebaseViewHolder>(){



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokemonFirebaseViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.pokemon_firebase_list, p0,false)

        return PokemonFirebaseViewHolder(view)


    }

    override fun getItemCount(): Int {
        return pokeFire.size
    }


    fun updateList(list: MutableList<PokemonFirebaseClass>){
        pokeFire = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: PokemonFirebaseViewHolder, position: Int) {



        var pokemons = pokeFire[position]

        var pName = holder.itemView.namePokemon
        var pNumber = holder.itemView.numberPokemon
        var pimgFront = holder.itemView.imgPokemonFront
        pName.text = pokemons.name!!.substring(0, 1).toUpperCase() + pokemons.name!!.substring(1)
        pNumber.text = "# " + pokemons.number
        var imageUrlFront = pokemons.imageFront
       Picasso.with(context).load(imageUrlFront).into(pimgFront)

        var deletePoke = holder.itemView.deletePokemonBtn

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userNode = firebaseUser!!.uid
        Log.w("Tag"," UserId : =====>>>> " + userNode)




        holder.itemView.setOnClickListener {

            val intent = Intent()
            intent.setClass(context, GameActivity::class.java)

            intent.putExtra("POKEMON_NUMBER", pokemons.name)
            intent.putExtra("POKEMON_NAME", pokemons.number)
            intent.putExtra("POKEMON_TYPES", pokemons.types)
            intent.putExtra("POKEMON_WEIGHT", pokemons.weight)
            intent.putExtra("POKEMON_HEIGHT", pokemons.height)
            intent.putExtra("POKEMON_URL", pokemons.imageFront)
            context.startActivity(intent)

        }



        deletePoke.setOnClickListener {

            var dialog: Dialog

            dialog = Dialog(context)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_delete_layout)

            dialog.show()

            var imgPokePop : ImageView = dialog.findViewById(R.id.imgVPopupDel)
            Picasso.with(context).load(imageUrlFront).into(imgPokePop)

            var txtVNamePoke : TextView = dialog.findViewById(R.id.namePokemonDel)
            txtVNamePoke.text = pokemons.name!!.substring(0, 1).toUpperCase() + pokemons.name!!.substring(1)


            //deletes pokemon after asking (in popup) if the user wants delete pokemn in her list
            var btnDeleteYesBtn : Button? = dialog.findViewById(R.id.deleteBtnPopUp)

            btnDeleteYesBtn?.setOnClickListener {
                val db = FirebaseDatabase.getInstance()
                val myRef = db.getReference(userNode)
                myRef.child(pokemons.name).removeValue()
                dialog.dismiss()
            }



            //cancel btn in pop up
            var btnCancelPopup: Button? = dialog?.findViewById(R.id.noBtnPopUp)
            btnCancelPopup?.isEnabled = true
            btnCancelPopup?.setOnClickListener {

                dialog?.cancel()

            }




        }





    }


    class PokemonFirebaseViewHolder(itemPokemon : View) : RecyclerView.ViewHolder(itemPokemon){



    }
}