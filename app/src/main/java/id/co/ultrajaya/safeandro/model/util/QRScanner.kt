package id.co.ultrajaya.safeandro.model.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.BarcodeFormat




class QRScanner : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    var _TAG: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //parameter dari activity yang memanggil scanner
        //dibuat ini karena kemungkinan dalam satu fragment terdapat 2 scanner result
        _TAG = intent.getStringExtra("tag")
        setupPermission()

        mScannerView = ZXingScannerView(this)

        val listFormat = ArrayList<BarcodeFormat>()
        listFormat.add(BarcodeFormat.QR_CODE) //set only 2D/QR CODE FORMAT ONLYE
        mScannerView!!.setFormats(listFormat)

        setContentView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(irawResult: Result?) {
        val aintent = Intent()
        val acode = irawResult!!.text
        aintent.putExtra("tag", _TAG)
        aintent.putExtra("code", acode)
        setResult(Activity.RESULT_OK, aintent)
        finish()
    }

    fun setupPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.CAMERA"), 50)
        }
    }
}