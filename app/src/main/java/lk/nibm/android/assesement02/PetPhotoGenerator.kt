package lk.nibm.android.assesement02

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class PetPhotoGenerator : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val pets = arrayOf("Select","Dog","Cat")
    lateinit var spnPets: Spinner
    lateinit var imgIcon: ImageView
    lateinit var btnGenerator: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_photo_generator)

        spnPets = findViewById(R.id.spnPet)
        imgIcon = findViewById(R.id.imgIcon)
        btnGenerator = findViewById(R.id.btnGenerate)

        val petAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pets)
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPets.adapter = petAdapter
        spnPets.onItemSelectedListener = this

        btnGenerator.setOnClickListener {
            generatePetPhoto()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        generatePetPhoto()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun generatePetPhoto() {
        val selectedPet = spnPets.selectedItem.toString()
        val apiUrl = "https://random.dog/woof.json"
        val catUrl = "https://api.thecatapi.com/v1/images/search"

        if(selectedPet =="Dog")
        { val request = JsonObjectRequest(Request.Method.GET, apiUrl, null,
            Response.Listener { response ->
                val imageUrl = response.getString("url")
                Picasso.get().load(imageUrl).into(imgIcon)
            },
            Response.ErrorListener { error ->

            })
        Volley.newRequestQueue(this).add(request)
        }
        else if (selectedPet =="Cat")
        { val request = JsonArrayRequest(Request.Method.GET, catUrl, null,
                Response.Listener { response ->
                    if (response.length() > 0) {
                        val catJsonObject = response.getJSONObject(0)
                        val catImage = catJsonObject.getString("url")
                        Picasso.get().load(catImage).into(imgIcon)
                    }
                },
                Response.ErrorListener { error ->
                })
            Volley.newRequestQueue(this).add(request)
        }
    }
}
