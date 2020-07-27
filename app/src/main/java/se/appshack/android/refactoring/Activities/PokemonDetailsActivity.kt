package se.appshack.android.refactoring.Activities

import android.animation.ValueAnimator
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import okhttp3.OkHttpClient
import okhttp3.Request
import se.appshack.android.refactoring.PokemonDetailsResponse
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.PokemonSpeciesResponse
import se.appshack.android.refactoring.R
import java.io.IOException
import java.util.*


class PokemonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        val pokemonName = intent.extras.getString("POKEMON_NAME")
        var namePokeUpper = pokemonName.toUpperCase()
        txtVNamePokemonDetail.text = namePokeUpper
        val pokemonUrl = intent.extras.getString("POKEMON_URL")
        val detailsTask = GetPokemonDetailsTask()
        detailsTask.execute(pokemonUrl)

        btnbackMainAct()
        imageAni()


    }
    internal inner class GetPokemonDetailsTask : AsyncTask<String?, Void?, PokemonDetailsResponse?>() {
        override fun doInBackground(vararg urls: String?): PokemonDetailsResponse? {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(urls[0].toString())
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()

          Log.w("URLS ", "URLSSS " + urls[0].toString())
            var response: PokemonDetailsResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                val jsonBody = httpResponse.body!!.string()

                Log.w("Success!!", " YES :  " + jsonBody)
                response = Gson().fromJson(jsonBody, PokemonDetailsResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return response
        }

        override fun onPostExecute(result: PokemonDetailsResponse?) {
            super.onPostExecute(result)


            val imageFront = findViewById<View>(R.id.imageFront) as ImageView
         Picasso.with(this@PokemonDetailsActivity).load(result!!.sprites?.urlFront).into(imageFront)
            val imageBack = findViewById<View>(R.id.imageBack) as ImageView
            Picasso.with(this@PokemonDetailsActivity).load(result.sprites?.urlBack).into(imageBack)

            (findViewById<View>(R.id.pokemonNumber) as TextView).text = String.format("#%s", result.id)



            val formattedName = result.name?.substring(0, 1)?.toUpperCase() + result.name?.substring(1)
            (findViewById<View>(R.id.pokemonName) as TextView).text = formattedName
            Collections.sort(result.types) { pokemonTypeModel, t1 -> pokemonTypeModel.slot - t1.slot }
            var types = ""
            for (i in result.types!!.indices) {
                val typeModel = result.types?.get(i)
                types += typeModel?.type?.name!!.substring(0, 1).toUpperCase() + typeModel.type!!.name!!.substring(1)
                if (i < result!!.types!!.size - 1) types += ", "




            }
            textSize()
            (findViewById<View>(R.id.pokemonTypes) as TextView).text = types
            (findViewById<View>(R.id.pokemonHeight) as TextView).text = String.format("%s decimetres", result.height)
            (findViewById<View>(R.id.pokemonWeight) as TextView).text = String.format("%s hectograms", result.weight)
            GetPokemonSpeciesDetailsTask().execute(result.species?.url)



            //add info about pokemon in firebase
            imgBtnAddList.setOnClickListener {
                postToRealTimeFirebase(result.name.toString(),
                        result.id.toString(),
                        types,
                        result.height.toString(),
                        result.weight.toString(),
                        result.sprites!!.urlFront.toString(),
                        result.sprites!!.urlBack.toString())

                val intent = Intent()
                intent.setClass(applicationContext, MyPokemonListActivity::class.java)
                startActivity(intent)

            }




        }



    }
    internal inner class GetPokemonSpeciesDetailsTask : AsyncTask<String?, Void?, PokemonSpeciesResponse?>() {
        override fun doInBackground(vararg urls: String?): PokemonSpeciesResponse? {

            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(urls[0].toString())
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()
            var response: PokemonSpeciesResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                val jsonBody = httpResponse.body!!.string()
                response = Gson().fromJson(jsonBody, PokemonSpeciesResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return response
        }

        override fun onPostExecute(result: PokemonSpeciesResponse?) {
            super.onPostExecute(result)
            var genus = ""
            for (genusModel in result!!.genera!!) {
                if (genusModel.language!!.name == "en") {
                    genus = genusModel.genus.toString()
                    break
                }
            }
            (findViewById<View>(R.id.pokemonSpecies) as TextView).text = genus
            findViewById<View>(R.id.loader).visibility = View.GONE
        }
        }

//back button to recyclerview
    fun btnbackMainAct(){

        backImgV.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



    }

    //changes text size
    fun textSize(){

        pokemonNumber.setOnClickListener {
            pokemonNumber.textSize = 30F

        }
        if (pokemonNumber.textSize.equals(60F)){
            pokemonNumber.setOnClickListener {
                pokemonNumber.textSize = 16F
            }

        }

    }

    //animation to the images
    fun imageAni(){

            val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.animation)
            imageFront.startAnimation(anim)
            imageBack.startAnimation(anim)


       }

//add/post/save to realtime database pokemon
    fun postToRealTimeFirebase(name : String, number : String, types: String, height : String , weight : String , imageFront: String, imageBack : String){


    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    var userNode = firebaseUser!!.uid
    Log.w("Tag"," UserId : =====>>>> " + userNode)

        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference(userNode)

        val pokemon = PokemonFirebaseClass(
                name,
                number,
                types,
                height,
                weight,
                imageFront,
                imageBack

        )


        val pushKey = myRef.push().key!!
        myRef.child(name).setValue(pokemon)

    }






}










/*
internal inner class GetPokemonDetailsTask : AsyncTask<String?, Void?, PokemonDetailsResponse?>() {
    protected override fun doInBackground(vararg urls: String): PokemonDetailsResponse? {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(urls[0])
                .get()
                .addHeader("Content-Type", "application/json; charset=utf8")
                .build()
        var response: PokemonDetailsResponse? = null
        try {
            val httpResponse = client.newCall(request).execute()
            val jsonBody = httpResponse.body!!.string()
            response = Gson().fromJson(jsonBody, PokemonDetailsResponse::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    override fun onPostExecute(result: PokemonDetailsResponse?) {
        super.onPostExecute(result)
        val imageFront = findViewById<View>(R.id.imageFront) as ImageView
        Picasso.with(this@PokemonDetailsActivity).load(result!!.sprites.urlFront).into(imageFront)
        val imageBack = findViewById<View>(R.id.imageBack) as ImageView
        Picasso.with(this@PokemonDetailsActivity).load(result.sprites.urlBack).into(imageBack)
        (findViewById<View>(R.id.pokemonNumber) as TextView).text = String.format("#%s", result.id)
        val formattedName = result.name.substring(0, 1).toUpperCase() + result.name.substring(1)
        (findViewById<View>(R.id.pokemonName) as TextView).text = formattedName
        Collections.sort(result.types) { pokemonTypeModel, t1 -> pokemonTypeModel.slot - t1.slot }
        var types = ""
        for (i in result.types.indices) {
            val typeModel = result.types[i]
            types += typeModel.type.name!!.substring(0, 1).toUpperCase() + typeModel.type.name!!.substring(1)
            if (i < result.types.size - 1) types += ", "
        }
        (findViewById<View>(R.id.pokemonTypes) as TextView).text = types
        (findViewById<View>(R.id.pokemonHeight) as TextView).text = String.format("%s decimetres", result.height)
        (findViewById<View>(R.id.pokemonWeight) as TextView).text = String.format("%s hectograms", result.weight)
        GetPokemonSpeciesDetailsTask().execute(result.species.url)
    }
}

internal inner class GetPokemonSpeciesDetailsTask : AsyncTask<String?, Void?, PokemonSpeciesResponse?>() {
    protected override fun doInBackground(vararg urls: String): PokemonSpeciesResponse? {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(urls[0])
                .get()
                .addHeader("Content-Type", "application/json; charset=utf8")
                .build()
        var response: PokemonSpeciesResponse? = null
        try {
            val httpResponse = client.newCall(request).execute()
            val jsonBody = httpResponse.body!!.string()
            response = Gson().fromJson(jsonBody, PokemonSpeciesResponse::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    override fun onPostExecute(result: PokemonSpeciesResponse?) {
        super.onPostExecute(result)
        var genus = ""
        for (genusModel in result!!.genera!!) {
            if (genusModel.language!!.name == "en") {
                genus = genusModel.genus.toString()
                break
            }
        }
        (findViewById<View>(R.id.pokemonSpecies) as TextView).text = genus
        findViewById<View>(R.id.loader).visibility = View.GONE
    }
}
*/
