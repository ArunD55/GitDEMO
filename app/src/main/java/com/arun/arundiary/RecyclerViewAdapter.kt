package com.arun.arundiary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arun.arundairy.R
import com.arun.arundiary.datasqlpackage.Dataclass
import com.arun.arundiary.pages.NewDiaryEntry

class RecyclerViewAdapter (var entryList:MutableList<Dataclass?>):
RecyclerView.Adapter<RecyclerViewAdapter.DiaryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DiaryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParent = false

        val view = inflater.inflate(R.layout.recyclerviewlist, parent, shouldAttachToParent )
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val item = entryList[position]
        holder.bindDiary(item)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    class DiaryViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        var view : View
        lateinit var diary : Dataclass
        var date : TextView
        var title : TextView
        var description : TextView

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context, NewDiaryEntry::class.java)
            intent.putExtra("IDofRow",diary.id)
            context.startActivity(intent)
        }


        init {
            view = v
            date = view.findViewById(R.id.date_text)
            title = view.findViewById(R.id.title_text)
            description=view.findViewById(R.id.desc_text)
            v.setOnClickListener(this)
        }
        fun bindDiary(diary : Dataclass?){
            this.diary = diary!!

            date.text = diary.date
            title.text = diary.title
            description.text= diary.description
        }
    }

}