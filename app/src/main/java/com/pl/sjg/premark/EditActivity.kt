package com.pl.sjg.premark

import android.app.Activity
import android.app.AlertDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import com.pl.sjg.premark.databinding.ActivityEditBinding
import com.pl.sjg.premark.frame.BaseActivity
import kotlinx.android.synthetic.main.activity_edit.*
import android.widget.Toast
import android.content.DialogInterface
import android.content.Intent
import android.text.TextUtils
import com.pl.sjg.premark.R.mipmap.ic_launcher




class EditActivity : BaseActivity(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.yes -> {
                if(TextUtils.isEmpty(et_content.text)){
                    alertEmpty()
                }else{
                    var sendIndex = intent.getIntExtra(SEND_CONTENT_INDEX , -1)
                    if(sendIndex >= 0){
                        var result : Intent = Intent()
                        result.putExtra(RETURN_CONTENT , et_content.text.toString())
                        result.putExtra(RETURN_CONTENT_INDEX , sendIndex)
                        setResult(Activity.RESULT_OK , result)
                        finish()
                    }

                }
            }
            R.id.no -> {
                alertCancel();
            }
        }
    }

    override fun initViews() {
        navigationBar.hidden()
        et_content.setText(intent.getStringExtra(SEND_CONTENT))

    }

    override fun initEvents() {
        yes.setOnClickListener(this)
        no.setOnClickListener(this)
    }

    lateinit var databind : ActivityEditBinding;

    override fun initDatas(view: View) {
        databind = DataBindingUtil.bind(view , null);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    fun alertCancel(){
        val dialog = AlertDialog.Builder(this)
                .setIcon(R.mipmap.peiqi)//设置标题的图片
                .setTitle("乖老婆")//设置对话框的标题
                .setMessage("亲亲的乖老婆,写的东西没保存哦,不保存就退出吗?")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .setPositiveButton("头铁就是不保存", DialogInterface.OnClickListener { dialog, which ->

                    dialog.dismiss()
                    setResult(Activity.RESULT_CANCELED , Intent())
                    popActivity()
                }).create()
        dialog.show()
    }

    fun alertEmpty(){
        val dialog = AlertDialog.Builder(this)
                .setIcon(R.mipmap.peiqi)//设置标题的图片
                .setTitle("乖老婆")//设置对话框的标题
                .setMessage("亲亲的乖老婆,没写东西嘛")//设置对话框的内容
                //设置对话框的按钮
                .setPositiveButton("好老公，忘写啦", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                }).create()
        dialog.show()
    }

}
