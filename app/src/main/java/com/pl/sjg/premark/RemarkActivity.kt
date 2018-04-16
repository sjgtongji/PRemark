package com.pl.sjg.premark

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pl.sjg.premark.databinding.ActivityRemarkBinding
import com.pl.sjg.premark.frame.BaseActivity
import com.pl.sjg.premark.frame.PrefUtils
import kotlinx.android.synthetic.main.activity_remark.*
import org.w3c.dom.Text

class RemarkActivity : BaseActivity() , View.OnClickListener{
    var remarks : MutableList<String> = mutableListOf<String>();
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add -> {
                var size : Int = PrefUtils().getInt(this , REMARK_NUM_KEY , 0);
                var intent = Intent(this@RemarkActivity , EditActivity::class.java)
                intent.putExtra(SEND_CONTENT , "")
                intent.putExtra(SEND_CONTENT_INDEX , size)
                pushActivityForResult(intent , 1)
            }
        }
    }

    lateinit var dataBind : ActivityRemarkBinding;
    override fun initViews() {
        navigationBar.hiddenButtons()
        navigationBar.setTitle("爱你的好老公")
        rv_list.layoutManager = GridLayoutManager(this, 1)
        add.attachToRecyclerView(rv_list)
    }

    override fun initEvents() {
        add.setOnClickListener(this)
    }



    override fun initDatas(view: View) {
        dataBind = DataBindingUtil.bind(view , null);
        initRemarks();

    }

    fun initRemarks(){
        remarks.clear()
        var size : Int = PrefUtils().getInt(this , REMARK_NUM_KEY , 0);
        var i : Int = 0;
        while(i < size){
            var content = PrefUtils().getString(this , REMARK_INDEX_KEY + i , "");
            if(!TextUtils.isEmpty(content)){
                remarks.add(content)
            }
            i++
        }
        rv_list.adapter = RemarkAdapter(remarks)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            var size : Int = PrefUtils().getInt(this , REMARK_NUM_KEY , 0);
            var index : Int = data!!.getIntExtra(RETURN_CONTENT_INDEX , -1);
            Log.e(TAG , "size : " + size)
            Log.e(TAG , "index : " + index)
            if(index >= 0){
                PrefUtils().putString(this , REMARK_INDEX_KEY + index , data!!.getStringExtra(RETURN_CONTENT))
                if(index == size){
                    PrefUtils().putInt(this , REMARK_NUM_KEY , size + 1)
                }

                initRemarks()
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remark)
    }

    inner class RemarkAdapter(val data : List<String>) : RecyclerView.Adapter<RemarkViewHolder>() , View.OnClickListener {
        override fun onBindViewHolder(p0: RemarkViewHolder?, p1: Int) {
            p0!!.remarkText.setOnClickListener(this)
            p0!!.remarkText.setTag(p1)
            p0!!.bind(data[p1]);
        }

        override fun getItemCount(): Int {
            return data.size;
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RemarkViewHolder {
            val layoutInflater = LayoutInflater.from(parent!!.context)
            val binding: ViewDataBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.adapter_remark, parent, false)

            val holder = RemarkViewHolder(binding);
            return holder
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.tv_remark -> {
                    var intent = Intent(this@RemarkActivity , EditActivity::class.java)
                    intent.putExtra(SEND_CONTENT , data[v.getTag() as Int])
                    intent.putExtra(SEND_CONTENT_INDEX , v.getTag() as Int)
                    pushActivityForResult(intent , 1)
                }
            }
        }

    }

    class RemarkViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
        var remarkText : TextView;
        init {
            remarkText = binding.root.findViewById(R.id.tv_remark) as TextView
        }
        fun bind(data : Any){
            binding.setVariable(BR.data , data)
            binding.executePendingBindings()
        }
    }
}
