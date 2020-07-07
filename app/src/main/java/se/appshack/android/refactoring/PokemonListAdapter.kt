package se.appshack.android.refactoring

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PokemonListAdapter(private val activity: Activity,  private var data: List<NamedResponseModel>) : RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PokemonViewHolder {
        return PokemonViewHolder(LayoutInflater.from(activity).inflate(R.layout.viewholder_pokemon_list, null, false))
    }

    override fun onBindViewHolder(pokemonViewHolder: PokemonViewHolder, i: Int) {
        val responseModel = data[i]
        pokemonViewHolder.bind(responseModel)

        pokemonViewHolder.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(activity, PokemonDetailsActivity::class.java)
            intent.putExtra("POKEMON_NAME", responseModel.name)
            intent.putExtra("POKEMON_URL", responseModel.url)
            activity.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateList(list: MutableList<NamedResponseModel>){

        data = list
        notifyDataSetChanged()


    }

}

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(pokemon: NamedResponseModel) {
        val number = String.format("#%s", pokemon.url!!.substring(pokemon.url!!.indexOf("pokemon/") + 8, pokemon.url!!.length - 1))
        (itemView.findViewById<View>(R.id.pokemon_number) as TextView).text = number
        val formattedName = pokemon.name!!.substring(0, 1).toUpperCase() + pokemon.name!!.substring(1)
        (itemView.findViewById<View>(R.id.pokemon_name) as TextView).text = formattedName

        var spritesModel=PokemonSpritesModel()

        Log.w("URL", "spritesModel.urlBack " + spritesModel.urlBack)




    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        itemView.setOnClickListener(listener)
    }




}