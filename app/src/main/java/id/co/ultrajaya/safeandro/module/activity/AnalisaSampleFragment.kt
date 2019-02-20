package id.co.ultrajaya.safeandro.module.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.model.util.LoadingDialog
import id.co.ultrajaya.safeandro.model.util.components.*
import id.co.ultrajaya.safeandro.module.contract.AnalisaSampleContract
import id.co.ultrajaya.safeandro.module.impl.AnalisaSampleImpl
import id.co.ultrajaya.safeandro.model.util.QRScanner
import id.co.ultrajaya.safeandro.model.util.ConfigComponents
import kotlinx.android.synthetic.main.activity_analisa_sample.*
import kotlinx.android.synthetic.main.component_edittext.view.*
import kotlinx.android.synthetic.main.component_edittext_multi.view.*
import kotlinx.android.synthetic.main.component_spinner.view.*
import java.util.*
import kotlin.collections.ArrayList
import id.co.ultrajaya.safeandro.model.util.Config

class AnalisaSampleFragment : Fragment(), AnalisaSampleContract.View, LayoutButtonContract,
    LayoutEditTextBarcodeContract, LayoutSpinnerContract {
    private lateinit var _analisaSampleImpl: AnalisaSampleImpl
    private lateinit var _itemAnalisaORC : ItemAnalisaQCORC

    private lateinit var _loadingDialog: LoadingDialog
    private lateinit var _alertDialog: AlertDialogCustom

    private var _isSpinner = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_analisa_sample, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _analisaSampleImpl = AnalisaSampleImpl(this)

        //view fragmentnya harus sudah selesai dulu baru bisa di inisialisasi disini
        //kalau diinisialisasi di awal langsung error
        _loadingDialog = LoadingDialog(activity!!)
        _alertDialog = AlertDialogCustom(activity!!)

        setComponent()
    }

    private fun setComponent() {
        LayoutEditTextBarcode(context, etBarcode, "Barcode", false, this)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditText(context, etItemSample, "Item Sample", true)
        LayoutEditText(context, etProdBatch, "Prodbatch", true)
        LayoutEditText(context, etIdMat, "ID Mat.", true)
        LayoutEditText(context, etNamaMat, "Nama Mat.", true)
        LayoutEditText(context, etLotNum, "Lotnum", true)
        LayoutEditText(context, etVol, "Volume", true, "number")
        LayoutSpinner(spTipeSample, "Tipe Sample", this, "sp_tipe")
        LayoutSpinner.setEnable(spTipeSample, false)
        LayoutSpinner(spHasil, "Hasil", this, "sp_hasil")
        LayoutSpinner.setEnable(spHasil, false)
        LayoutEditTextMulti(context, etItemORC, "Test ID", "Sequence", true, true, "number", "number")
        LayoutEditText(context, etStandard, "Standar", true, "text")
        LayoutEditText(context, etHasil, "Hasil", true, "decimal")
        LayoutButton(btSaveBarcode, "SAVE", this, "savebarcode")
        LayoutButton.setEnable(btSaveBarcode, false, 0)
    }

    private fun callScanner(itag: String) {
        val aintent = Intent(activity, QRScanner::class.java)
        aintent.putExtra("tag", itag)
        startActivityForResult(aintent, 1)
    }

    override fun onClickButtonBarcode() {
        callScanner("qr_utama")
    }

    override fun onEnterKeyBarcode(icode: String) {
        _analisaSampleImpl.onGetInfoBarcode(icode)
    }

    override fun setDataInfoBarcode(itemMaterial: ItemMaterial) {
        //LayoutEditTextBarcode.setEnable(etBarcode, false)
        LayoutEditText.setText(etItemSample, itemMaterial.item_sample)
        LayoutEditText.setText(etProdBatch, itemMaterial.prodbatch)
        LayoutEditText.setText(etIdMat, itemMaterial.produk)
        LayoutEditText.setText(etNamaMat, itemMaterial.nama)
        LayoutEditText.setText(etLotNum, itemMaterial.lotmanual)
        LayoutEditText.setText(etVol, ConfigComponents.convertStringToCurr(itemMaterial.vollt.toString()))
        LayoutEditText.setText(etHasil, "")
        _analisaSampleImpl.onGetListTipeAnalisaSample(etItemSample.editText.text.toString())
    }

    override fun setListDataTipeAnalisaSample(itipeAnalisaSampleList : ArrayList<ItemSpinner>) {
        LayoutSpinner.setEnable(spTipeSample, true)
        LayoutSpinner.setListItemSpinner(activity!!, spTipeSample, itipeAnalisaSampleList)
        LayoutSpinner.setFocus(spTipeSample)
        LayoutButton.setEnable(btSaveBarcode, true, R.drawable.ic_save)

        LayoutEditTextMulti.setText(etItemORC.editText1, "")
        LayoutEditTextMulti.setText(etItemORC.editText2, "")
        LayoutEditText.setText(etStandard, "")
        LayoutEditText.setEnable(etHasil, false)
        LayoutEditText.setText(etHasil, "")
        LayoutEditText.setVisibility(etHasil, true)
        LayoutSpinner.setVisibility(spHasil, false)
    }

    override fun setListHasilAnalisaSample(ihasilAnalisaSampleList: List<ItemSpinner>) {
        //jika null maka numeric jika tidak maka spinner with list value
        if (ihasilAnalisaSampleList[0].spinner == null) {
            _isSpinner = false
            spHasil.visibility = View.GONE
            etHasil.visibility = View.VISIBLE
            LayoutEditText.setEnable(etHasil, true)
            LayoutEditText.setFocus(etHasil, activity, true)
        } else {
            _isSpinner = true
            spHasil.visibility = View.VISIBLE
            etHasil.visibility = View.GONE
            LayoutSpinner.setEnable(spHasil, true)
            LayoutSpinner.setListItemSpinner(activity!!, spHasil, ihasilAnalisaSampleList as ArrayList<ItemSpinner>)
            LayoutSpinner.setFocus(spHasil)
        }
    }

    override fun onChangeItemSpinnerListener(iselectedItem: String, itag: String) {
        when (itag) {
            "sp_tipe" -> {
                _analisaSampleImpl.onGetDetailLOV(iselectedItem)
            }
            "sp_hasil" -> {
            }
            else -> {
                showToast("Tidak ada id tag spinner tersebut")
            }
        }
    }

    override fun setDetailLOV(itemAnalisaQCORC: ItemAnalisaQCORC) {
        _itemAnalisaORC = itemAnalisaQCORC
        LayoutEditTextMulti.setText(etItemORC.editText1, _itemAnalisaORC.testid.toString())
        LayoutEditTextMulti.setText(etItemORC.editText2, _itemAnalisaORC.seq.toString())
        if(itemAnalisaQCORC.target != null){
            LayoutEditText.setText(etStandard, _itemAnalisaORC.target)
        }else{
            LayoutEditText.setText(etStandard, _itemAnalisaORC.minvaluenum.toString() + " - " + _itemAnalisaORC.maxvaluenum.toString())
        }
        _analisaSampleImpl.onGetLOVResultAnalisaSampleORC(_itemAnalisaORC.testid.toString())
    }

    //coba pake abstrack class dengan body yang otomatis implement
    //coba pake singleton class instance
    override fun showLoading() {
        _loadingDialog.show()
    }

    override fun hideLoading() {
        _loadingDialog.hide()
    }

    override fun showAlertDialog(imsg: String, itipe: Int) {
        _alertDialog.showAlertDialog(imsg, itipe)
    }

    override fun showToast(imsg: String?) {
        Toast.makeText(activity, imsg, Toast.LENGTH_SHORT).show()
    }

    override fun showAlertDialogAction(imsg: String, itipe: Int, itag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostAlertDialogAction(itag: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearForm() {
        val alistOfView = Arrays.asList(
            etProdBatch.editText, etItemSample.editText, etIdMat.editText, etNamaMat.editText,
            etLotNum.editText, etVol.editText, etHasil.editText, etItemORC.editText1, etItemORC.editText2, etStandard.editText
        )
        ConfigComponents.clearFormEditText(alistOfView)
        LayoutSpinner.setEmpty(spTipeSample)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditTextBarcode.setText(etBarcode, "")
        LayoutButton.setEnable(btSaveBarcode, false, 0)
        LayoutEditText.setEnable(etHasil, false) //ditaruh di bawah barcode focus karena dapat event un focus
        //tanyakan di stack over flow
        //ConfigComponents.refreshFragment(fragmentManager!!, AnalisaSampleFragment::class.java)
        /*lateinit var afragment: Fragment
        val afragmentClass: Class<*> = AnalisaSampleFragment::class.java
        try {
            afragment = afragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fragmentManager = fragmentManager
        fragmentManager!!.beginTransaction().replace(R.id.content_frame, afragment).commit()*/
    }

    override fun clearFormPostSave() {
        LayoutEditTextMulti.setText(etItemORC.editText1, "")
        LayoutEditTextMulti.setText(etItemORC.editText2, "")

        LayoutEditText.setVisibility(etHasil, true)
        LayoutEditText.setEnable(etHasil, false)

        LayoutEditText.setText(etStandard, "")
        LayoutEditText.setText(etHasil, "")

        LayoutSpinner.setEmpty(spHasil)
        LayoutSpinner.setVisibility(spHasil, false)
    }

    override fun onClickButton(itag: String) {
        when (itag) {
            "savebarcode" -> {
                var atipe: String = spTipeSample.spinner.selectedItem.toString()
                var atestID : String = LayoutEditTextMulti.getText(etItemORC.editText1)
                var aseq : String = LayoutEditTextMulti.getText(etItemORC.editText2)
                var astandar : String = LayoutEditText.getText(etStandard)
                var aisAccept : Boolean

                var ahasil: String
                if (_isSpinner) {
                    ahasil = LayoutSpinner.getText(spHasil)
                    aisAccept = ahasil.equals(_itemAnalisaORC.target)
                } else {
                    ahasil = LayoutEditText.getText(etHasil)
                    if(ahasil.toDouble() >= _itemAnalisaORC.minvaluenum!! && ahasil.toDouble() <= _itemAnalisaORC.maxvaluenum!!){
                        aisAccept = true
                    }else{
                        showAlertDialog("Hasil " + ahasil +" tidak sesuai range standard !", 1)
                        return
                    }
                }

                if (atipe.isNotEmpty() && !atipe.equals(Config.HEADER_ITEM_SPINNER)
                    && ahasil.isNotEmpty() &&!ahasil.equals(Config.HEADER_ITEM_SPINNER)
                    && atestID.isNotEmpty() && aseq.isNotEmpty() && astandar.isNotEmpty()) {

                    var aitemAnalisaQCORC = ItemAnalisaQCORC()
                    aitemAnalisaQCORC.seq = aseq.toInt()
                    aitemAnalisaQCORC.testid = atestID.toInt()
                    aitemAnalisaQCORC.target = astandar //ini pake target saja sebenarnya nilainya bisa yang max/min value itu
                    _analisaSampleImpl.onSaveDataBarcode(atipe, ahasil, aisAccept, aitemAnalisaQCORC)
                } else {
                    showAlertDialog("Masih ada data yang kosong", 1)
                }
            }
            else -> showToast("Tidak id button tersebut")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val atag = data!!.getStringExtra("tag")
                val code = data.getStringExtra("code")
                when (atag) {
                    "qr_utama" -> {
                        LayoutEditTextBarcode.setText(etBarcode, code)
                        _analisaSampleImpl.onGetInfoBarcode(code)
                    }
                }
            }
        }
    }
}