package se.appshack.android.refactoring.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.my_message_right.view.*
import se.appshack.android.refactoring.ModelClasses.ChattClass
import se.appshack.android.refactoring.R

class ChattAdapter(val context: Context, val chattList: List<ChattClass>) : RecyclerView.Adapter<ChattAdapter.MyViewHolder>() {

    var MESSAGE_TYPE_LEFT = 0
    var MESSAGE_TYPE_RIGHT = 1
    var MESSAGE_TYPE_RIGHT_GREYCOLOR = 2
    var firebaseUser: FirebaseUser? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        if (viewType == MESSAGE_TYPE_LEFT) {
            val view = LayoutInflater.from(context).inflate(R.layout.my_message_right, parent, false)
            return MyViewHolder(view)
        } else if (viewType == MESSAGE_TYPE_RIGHT) {
            val view = LayoutInflater.from(context).inflate(R.layout.their_message_left, parent, false)
            return MyViewHolder(view)

        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.others_message, parent, false)
            return MyViewHolder(view)
        }


    }

    override fun getItemCount(): Int {

        return chattList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val chattClass = chattList[position]
        holder.setData(chattClass)

        Log.w("Chatt", "Chatt position " + chattClass)

        var message = chattList[position].message
        var firstName = chattList[position].name

        holder.itemView.showName.text = firstName
        holder.itemView.showMessage.text = message


        holder.itemView.setOnClickListener {


        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(chatt: ChattClass) {

            itemView.setOnClickListener {


            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (chattList.get(position).sender.equals(firebaseUser?.uid)) {


            return MESSAGE_TYPE_RIGHT
        } else if (!chattList.get(position).sender.equals(firebaseUser?.uid)) {

            return MESSAGE_TYPE_LEFT
        } else {

            return MESSAGE_TYPE_RIGHT_GREYCOLOR
        }


    }


}