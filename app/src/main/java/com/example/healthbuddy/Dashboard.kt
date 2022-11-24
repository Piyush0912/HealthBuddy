package com.example.healthbuddy

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Size
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class Dashboard : AppCompatActivity(), PaymentResultListener {
    lateinit var textView : TextView
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        Checkout.preload(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        textView = findViewById(R.id.textView1)
        val getShared : SharedPreferences = getSharedPreferences("demo", MODE_PRIVATE)
//        val xyz : SharedPreferences = getSharedPreferences("bed", MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
//        val uid = getShared.getString("userid","NULL").toString()
        val name = getShared.getString("name","NULL")
//        Toast.makeText(this, "hello$phoneNumber", Toast.LENGTH_SHORT).show()
//        val name = getShared.getString("name","NULL")
//        val email = getShared.getString("email","NULL")
//        val ward = xyz.getString("wardType","NULL")
//        val date = xyz.getString("dateofAdmit","NULL")
        val text1 = "Hello,<br><strong>$name!</strong>"
        textView.setText(Html.fromHtml(text1))
        val alertDialogBuilder = AlertDialog.Builder(this)
        firebaseAuth.addAuthStateListener(FirebaseAuth.AuthStateListener {
            if(firebaseAuth.currentUser==null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        })
    }
    fun Pay(view: View) {
        val checkout = Checkout()
        val activity: Activity = this
        checkout.setKeyID("rzp_test_ZhWffE4g8Fxp7s");
        try {
            val options = JSONObject()
            options.put("name","HealthBuddy")
            options.put("description","Bed_Allotment Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount","50000")//pass amount in currency subunits
            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            val prefill = JSONObject()
            prefill.put("email","piyush74bansal@gmail.com")
            prefill.put("contact","9548746883")

            options.put("prefill",prefill)
            checkout.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this, "Error in payment: "+e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment successful $p0", Toast.LENGTH_SHORT).show()
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error $p1 ", Toast.LENGTH_SHORT).show()
    }
    fun Bedallotment(view: View) {
        val intent = Intent(this, WardsDetail::class.java)
        startActivity(intent)
    }
    fun onlineConsultation(view: View) {

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.profile -> {
//                Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,Profile::class.java)
                startActivity(intent)
                true;
            }
            R.id.BedAvailability -> {
//                Toast.makeText(this, "Bead Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,BedAvailability::class.java)
                startActivity(intent)
                true;
            }
            R.id.Logout -> {
                Logout()
                true;
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun Logout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to Log Out")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            firebaseAuth.signOut()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    fun ConsulntNow(view: View) {
        val intent = Intent(this,ConsultNow::class.java)
        startActivity(intent)
    }
}