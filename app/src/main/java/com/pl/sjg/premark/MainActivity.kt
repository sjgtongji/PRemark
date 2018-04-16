package com.pl.sjg.premark

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.pl.sjg.premark.R.id.button
import com.pl.sjg.premark.databinding.ActivityMainBinding
import com.pl.sjg.premark.frame.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() ,View.OnClickListener{
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.button -> {
                pushActivity(RemarkActivity::class.java , true);
            }
        }
    }

    override fun initViews() {
        navigationBar.hidden()
    }

    override fun initEvents() {
        button.setOnClickListener(this)
    }
    lateinit var dataBind : ActivityMainBinding;
    override fun initDatas(view: View) {
        dataBind = DataBindingUtil.bind(view , null);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)
    }
}
