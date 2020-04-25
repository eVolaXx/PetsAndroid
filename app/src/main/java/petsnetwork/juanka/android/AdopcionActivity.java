package petsnetwork.juanka.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import petsnetwork.juanka.AddDogAdopcion;
import petsnetwork.juanka.android.fragments.InicioFragment;

//Clase principal para las adopciones
public class AdopcionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView all_users_list_dogs;
    private FloatingActionButton add_dogs_adoption;

    //Firebase
    private DatabaseReference UsersRef, AdoptionReference;
    private StorageReference UserImageRef;
    private FirebaseAuth mAuth;
    private String currentUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopcion);
        MostrarTodasLasAdopciones();


        // Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        AdoptionReference = FirebaseDatabase.getInstance().getReference().child("Adopciones");


        toolbar = (Toolbar) findViewById(R.id.toolbar_adopcion);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Perros en Adopción");
        all_users_list_dogs = (RecyclerView) findViewById(R.id.all_dogs_post_list);
        add_dogs_adoption = (FloatingActionButton) findViewById(R.id.add_dogs_adoption);


        add_dogs_adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui cambiamos a ventana de añadir perro en adopcion
                Intent intentAddAdoption = new Intent(AdopcionActivity.this, AddDogAdopcion.class);
                //intentComment.putExtra("PostKey", PostKey);
                startActivity(intentAddAdoption);


            }
        });


    }

    // Firebase Adopciones: Añadir los perros a Firebase y mostrarlos en la pantalla principal rellenando con Viewholder los objetos
    private void MostrarTodasLasAdopciones() {
        Query pos = AdoptionReference.orderByChild("contador");
        FirebaseRecyclerOptions<PerrosAdopcionInfo> options = new FirebaseRecyclerOptions.Builder<PerrosAdopcionInfo>().setQuery(pos, PerrosAdopcionInfo.class).build();
        FirebaseRecyclerAdapter<PerrosAdopcionInfo, AdopcionesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PerrosAdopcionInfo, AdopcionesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdopcionesViewHolder adopcionesViewHolder, int i, @NonNull PerrosAdopcionInfo perrosAdopcionInfo) {
                //final String PostKey = getRef(i).getKey();


                adopcionesViewHolder.user.setText(perrosAdopcionInfo.getUser());
                adopcionesViewHolder.time.setText("" + perrosAdopcionInfo.getTime());
                adopcionesViewHolder.date.setText("" + perrosAdopcionInfo.getDate());
                adopcionesViewHolder.contacto.setText(perrosAdopcionInfo.getContacto());
                adopcionesViewHolder.raza.setText(perrosAdopcionInfo.getRace());
                adopcionesViewHolder.dog_name.setText(perrosAdopcionInfo.getDogName());
                adopcionesViewHolder.description.setText(perrosAdopcionInfo.getDescription());


                Picasso.get().load(perrosAdopcionInfo.getDogImage())
                        .resize(0, 1200)
                        .into(adopcionesViewHolder.dogImage);

            }

            @NonNull
            @Override
            public AdopcionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_post_dog_adoption, parent, false);
                AdopcionesViewHolder viewHolder = new AdopcionesViewHolder(view);

                return viewHolder;
            }
        };

        all_users_list_dogs.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }


    public static class AdopcionesViewHolder extends RecyclerView.ViewHolder {
        TextView user, date, time, description, contacto, raza, dog_name;
        CircleImageView user_post_image;
        ImageView dogImage;
        String currentIdUsuario;


        public AdopcionesViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.post_usuario_adoption);
            dog_name = itemView.findViewById(R.id.adoption_name);
            date = itemView.findViewById(R.id.post_date_adoption);
            time = itemView.findViewById(R.id.post_time_adoption);
            description = itemView.findViewById(R.id.post_descripcion_adoption);
            contacto = itemView.findViewById(R.id.adoption_contacto);
            raza = itemView.findViewById(R.id.adoption_raza);
            user_post_image = itemView.findViewById(R.id.post_profile_adoption);
            dogImage = itemView.findViewById(R.id.post_dog_image);

        }

    }
}
