package petsnetwork.juanka.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import petsnetwork.juanka.AddDogAdopcion

//Clase principal para las adopciones
class AdopcionActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var all_users_list_dogs: RecyclerView? = null
    private var add_dogs_adoption: FloatingActionButton? = null
    private var search_dogs_adoption: FloatingActionButton? = null

    //Firebase
    private var UsersRef: DatabaseReference? = null
    private var AdoptionReference: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUserID: String? = null
    private var mAdapter: FirebaseRecyclerAdapter<PerrosAdopcionInfo, AdopcionesViewHolder>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopcion)
        //MostrarTodasLasAdopciones()


        // Firebase
        mAuth = FirebaseAuth.getInstance()
        currentUserID = mAuth!!.currentUser!!.uid
        UsersRef = FirebaseDatabase.getInstance().reference.child("Users")
        AdoptionReference = FirebaseDatabase.getInstance().reference.child("Adopciones")
        toolbar = findViewById<View>(R.id.toolbar_adopcion) as Toolbar
        setSupportActionBar(toolbar)
        toolbar!!.title = "Perros en Adopción"
        all_users_list_dogs = findViewById<View>(R.id.all_dogs_post_list) as RecyclerView
        add_dogs_adoption = findViewById<View>(R.id.add_dogs_adoption) as FloatingActionButton
        search_dogs_adoption = findViewById<View>(R.id.search_dogs_adoption_location) as FloatingActionButton
        add_dogs_adoption!!.setOnClickListener { // Aqui cambiamos a ventana de añadir perro en adopcion
            val intentAddAdoption = Intent(this@AdopcionActivity, AddDogAdopcion::class.java)
            //intentComment.putExtra("PostKey", PostKey);
            startActivity(intentAddAdoption)
        }
        // Buscar y filtar perros en adopcion por localidad
        search_dogs_adoption!!.setOnClickListener { }
    }

    // Firebase Adopciones: Añadir los perros a Firebase y mostrarlos en la pantalla principal rellenando con Viewholder los objetos
    private fun MostrarTodasLasAdopciones() {
        val query = AdoptionReference!!.orderByChild("contador")
        val options = FirebaseRecyclerOptions.Builder<PerrosAdopcionInfo>().setQuery(query, PerrosAdopcionInfo::class.java).build()
        mAdapter = object : FirebaseRecyclerAdapter<PerrosAdopcionInfo, AdopcionesViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            override fun onBindViewHolder(adopcionesViewHolder: AdopcionesViewHolder, i: Int, perrosAdopcionInfo: PerrosAdopcionInfo) {
                //final String PostKey = getRef(i).getKey();
                adopcionesViewHolder.user.text = perrosAdopcionInfo.getUser()
                Picasso.get().load(perrosAdopcionInfo.getProfileimage()).into(adopcionesViewHolder.user_post_image_adoption)
                adopcionesViewHolder.time.text = "" + perrosAdopcionInfo.getTime()
                adopcionesViewHolder.date.text = "" + perrosAdopcionInfo.getDate()
                adopcionesViewHolder.edad.text = "" + perrosAdopcionInfo.edad
                adopcionesViewHolder.location.text = "" + perrosAdopcionInfo.getLocation()
                adopcionesViewHolder.contact.text = perrosAdopcionInfo.contacto
                adopcionesViewHolder.race.text = perrosAdopcionInfo.getRace()
                adopcionesViewHolder.dog_name.text = perrosAdopcionInfo.dogName
                adopcionesViewHolder.description.text = perrosAdopcionInfo.getDescription()
                Picasso.get().load(perrosAdopcionInfo.dogImage)
                        .resize(0, 1200)
                        .into(adopcionesViewHolder.dogImage)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdopcionesViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.all_post_dog_adoption, parent, false)
                return AdopcionesViewHolder(view)
            }
        }
        all_users_list_dogs!!.adapter = mAdapter
        mAdapter!!.startListening()


    }


    class AdopcionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var user: TextView
        var date: TextView
        var time: TextView
        var edad: TextView
        var description: TextView
        var location: TextView
        var contact: TextView
        var race: TextView
        var dog_name: TextView
        var user_post_image_adoption: CircleImageView
        var dogImage: ImageView
        var currentIdUsuario: String? = null

        init {
            user = itemView.findViewById(R.id.post_usuario_adoption)
            dog_name = itemView.findViewById(R.id.dog_name)
            date = itemView.findViewById(R.id.post_date_adoption)
            time = itemView.findViewById(R.id.post_time_adoption)
            edad = itemView.findViewById(R.id.post_adoption_edad)
            description = itemView.findViewById(R.id.dog_description)
            location = itemView.findViewById(R.id.dog_location)
            contact = itemView.findViewById(R.id.dog_contact)
            race = itemView.findViewById(R.id.dog_race)
            user_post_image_adoption = itemView.findViewById(R.id.post_profile_adoption)
            dogImage = itemView.findViewById(R.id.dog_imageview)
        }
    }
}