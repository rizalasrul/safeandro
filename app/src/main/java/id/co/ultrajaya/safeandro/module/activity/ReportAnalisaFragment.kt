package id.co.ultrajaya.safeandro.module.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.model.response.general.ItemSpinner
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.model.util.LoadingDialog
import id.co.ultrajaya.safeandro.model.util.components.*
import id.co.ultrajaya.safeandro.module.contract.ReportAnalisaContract
import id.co.ultrajaya.safeandro.module.impl.ReportAnalisaImpl
import id.co.ultrajaya.safeandro.model.util.QRScanner
import id.co.ultrajaya.safeandro.model.util.ConfigComponents
import id.co.ultrajaya.safeandro.module.adapter.ReportAnalisaAdapter
import kotlinx.android.synthetic.main.activity_report_analisa.*
import kotlinx.android.synthetic.main.activity_report_analisa_modal.view.*
import kotlinx.android.synthetic.main.component_button_multi.view.*
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQC
import id.co.ultrajaya.safeandro.model.response.general.ItemAnalisaQCORC
import kotlinx.android.synthetic.main.component_edittext.view.*
import kotlinx.android.synthetic.main.component_edittext_multi.view.*
import kotlinx.android.synthetic.main.component_recycleview.view.*
import kotlinx.android.synthetic.main.component_spinner.view.*
import java.util.*
import kotlin.collections.ArrayList
import id.co.ultrajaya.safeandro.model.util.Config

class ReportAnalisaFragment : Fragment(), ReportAnalisaContract.View,
    LayoutEditTextBarcodeContract, LayoutButtonContract, LayoutSpinnerContract {
    private lateinit var _reportAnalisaImpl: ReportAnalisaImpl

    private lateinit var _loadingDialog: LoadingDialog
    private lateinit var _alertDialog: AlertDialogCustom
    private lateinit var _modalView: View

    var _reportAnalisaList: ArrayList<ItemMaterial> = ArrayList()

    lateinit var _reportAnalisaAdapter: ReportAnalisaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_report_analisa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _reportAnalisaImpl = ReportAnalisaImpl(this)

        _loadingDialog = LoadingDialog(activity!!)
        _alertDialog = AlertDialogCustom(activity!!)

        setComponent()
    }

    private fun setComponent() {
        rvListAnalisa.recycleView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _reportAnalisaAdapter = ReportAnalisaAdapter(activity!!, _reportAnalisaList, this)
        rvListAnalisa.recycleView.adapter = _reportAnalisaAdapter

        LayoutEditTextBarcode(context, etBarcode, "Barcode", false, this)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutEditTextMulti(context, etIdRef_ItemSample, "Item Sample", "ID Ref.", true, true, "text", "number")
        LayoutEditTextMulti(context, etPB_Vol, "Prodbatch", "Volume.", true, true, "text", "number")
        //LayoutEditText(context, etProdBatch, "Prodbatch", true)
        LayoutEditText(context, etWIP, "WIP", true)
        //LayoutEditText(context, etNamaMat, "Nama Mat.", true)
        LayoutEditText(context, etLotNum, "Lotnum", true)
        //LayoutEditText(context, etVol, "Volume", true, "number")
        LayoutRecyclerview(rvListAnalisa, "List Hasil Analisa")
        LayoutButtonMulti(btProses.button1, "Reject", this, "deleteresume")
        LayoutButtonMulti.setEnable(btProses.button1, false, 0, ContextCompat.getColor(activity!!, R.color.red))
        LayoutButtonMulti(btProses.button2, "Approve", this, "saveresume")
        LayoutButtonMulti.setEnable(btProses.button2, false, 0, ContextCompat.getColor(activity!!, R.color.colorAccent))
    }

    private fun callScanner(itag: String) {
        val aintent = Intent(activity, QRScanner::class.java)
        aintent.putExtra("tag", itag)
        startActivityForResult(aintent, 1)
    }

    override fun onClickButtonBarcode() {
        callScanner("qr_beaker")
    }

    override fun onEnterKeyBarcode(icode: String) {
        if (icode.equals("")) {
            showAlertDialog("Ada data yang masih kosong !", 2)
            clearForm()
        } else {
            _reportAnalisaImpl.onGetInfoBarcode(icode)
        }
    }

    override fun setDataInfoBarcode(itemMaterialList: List<ItemMaterial>) {
        LayoutEditTextMulti.setText(etIdRef_ItemSample.editText1, itemMaterialList[0].item_sample)
        LayoutEditTextMulti.setText(etIdRef_ItemSample.editText2, itemMaterialList[0].idref.toString())

        LayoutEditTextMulti.setText(etPB_Vol.editText1, itemMaterialList[0].prodbatch)
        LayoutEditTextMulti.setText(etPB_Vol.editText2, ConfigComponents.convertStringToCurr(itemMaterialList[0].vollt.toString()))

        LayoutEditText.setText(etWIP, itemMaterialList[0].produk + " - " + itemMaterialList[0].nama )
        //LayoutEditText.setText(etNamaMat, itemMaterialList[0].nama)
        LayoutEditText.setText(etLotNum, itemMaterialList[0].lotmanual) //dihide
        //LayoutEditText.setText(etVol, ConfigComponents.convertStringToCurr(itemMaterialList[0].vollt.toString()))
        LayoutButtonMulti.setEnable(btProses.button1, true, R.drawable.ic_cancel, ContextCompat.getColor(activity!!, R.color.red))
        LayoutButtonMulti.setEnable(btProses.button2, true, R.drawable.ic_save, ContextCompat.getColor(activity!!, R.color.colorAccent))

        //harus pakai metode ini karena kalau pakai _reportAnalisaList = itemMaterialList akan copy address reference bukan datanya
        _reportAnalisaList.clear()
        _reportAnalisaList.addAll(itemMaterialList)
        if (_reportAnalisaList.size > 0) {
            hideErrorRV()
        } else {
            showErrorRV()
        }
        onRefreshList()
        //tidak jadi digunakan karena sudah diambil di bagian analisa
        //_reportAnalisaImpl.onGetInfoBarcodeORC(itemMaterialList[0].item_sample!!)
    }

    override fun onClickButton(itag: String) {
        when (itag) {
            "saveresume" -> {
                showAlertDialogAction("[APPROVE] Masukkan data berikut ini", 3, "save")
            }
            "deleteresume" -> {
                showAlertDialogAction("[REJECT] Masukkan data berikut ini", 3, "delete")
            }
            else -> showToast("Tidak tag id button tersebut")
        }
    }

    override fun onRefreshList() {
        _reportAnalisaAdapter.notifyDataSetChanged()
    }

    override fun showErrorRV() {
        LayoutRecyclerview.setError(rvListAnalisa, true)
    }

    override fun hideErrorRV() {
        LayoutRecyclerview.setError(rvListAnalisa, false)
    }

    override fun showAlertDialogWithOptions(imsg: String, position: Int) {
        //dalam hal ini tidak digunakan karena tidak ada fitur delete row
    }

    override fun showAlertDialogAction(imsg: String, itipe: Int, itag: String) {
        _modalView = getLayoutInflater().inflate(R.layout.activity_report_analisa_modal, null)
        LayoutEditText(context, _modalView.etIdRef, "ID Ref.", true, "number")
        LayoutEditText(activity, _modalView.etItemSample, "Item Sample", true)

        //dapat id ref dan item sample dari get barcode awal di assign
        LayoutEditText.setText(_modalView.etItemSample, etIdRef_ItemSample.editText1.text.toString())
        LayoutEditText.setText(_modalView.etIdRef, etIdRef_ItemSample.editText2.text.toString())

        LayoutSpinner(_modalView.spTipeSample, "Result", this, "sp_result")

        var alistItemTipeResult = ArrayList<ItemSpinner>()
        var aitemSpinner: ItemSpinner

        if (itag.equals("save")) {
            var arrayItem = arrayOf("Acc", "Acc With Varian")
            arrayItem.forEach {
                aitemSpinner = ItemSpinner()
                aitemSpinner.spinner = it
                alistItemTipeResult.add(aitemSpinner)
            }
        } else {
            aitemSpinner = ItemSpinner()
            aitemSpinner.spinner = "Koreksi"
            alistItemTipeResult.add(aitemSpinner)
        }

        LayoutSpinner.setListItemSpinner(activity!!, _modalView.spTipeSample, alistItemTipeResult)

        LayoutEditText(activity, _modalView.etNotes, "Notes", false)
        LayoutEditText.setFocus(_modalView.etNotes, context, false)

        LayoutEditText.setLines(_modalView.etNotes, 5)
        LayoutSpinner.setFocus(_modalView.spTipeSample)
        var alertDialog = AlertDialogCustom(activity!!, _modalView, this, itag)
        alertDialog.showAlertDialog(imsg, 3)
    }

    override fun onChangeItemSpinnerListener(iselectedItem: String, itag: String) {
        _modalView.etNotes.requestFocus()
    }

    override fun onPostAlertDialogAction(itag: String) {
        var aidref = _modalView.etIdRef.editText.text.toString()
        var aitemSample = _modalView.etItemSample.editText.text.toString()
        var aresult = _modalView.spTipeSample.spinner.selectedItem.toString()
        var anotes = _modalView.etNotes.editText.text.toString()

        if (aresult == "" || anotes == "" || aresult == Config.HEADER_ITEM_SPINNER) {
            showAlertDialog("Masih ada data yang kosong !", 1)
            return
        }

        var aitemAnalisaQC = ItemAnalisaQC()
        aitemAnalisaQC.idRef = aidref
        aitemAnalisaQC.item_sample = aitemSample
        aitemAnalisaQC.result = aresult
        aitemAnalisaQC.note = anotes

        when (itag) {
            //dalam hal ini sebenarnya tidak udah dibedakan tidak aapa apa karena SP SAVE nya sama
            //tapi ini digunakan in case jika ada double post alert dialog action after
            "save" -> {
                _reportAnalisaImpl.onSaveInfoBarcode(aitemAnalisaQC)
                _reportAnalisaImpl.onSaveInfoBarcodeORC(aitemSample, _reportAnalisaList)
            }
            "delete" -> {
                _reportAnalisaImpl.onSaveInfoBarcode(aitemAnalisaQC)
            }
        }
    }

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

    override fun clearForm() {
        val alistOfView = Arrays.asList(
            etIdRef_ItemSample.editText1,
            etIdRef_ItemSample.editText2,
            etPB_Vol.editText1,
            etPB_Vol.editText2,
            etWIP.editText,
            //etNamaMat.editText,
            etLotNum.editText
            //etVol.editText
        )
        ConfigComponents.clearFormEditText(alistOfView)
        LayoutEditTextBarcode.setFocus(etBarcode)
        LayoutButtonMulti.setEnable(btProses.button1, false, 0, ContextCompat.getColor(activity!!, R.color.red))
        LayoutButtonMulti.setEnable(btProses.button2, false, 0, ContextCompat.getColor(activity!!, R.color.colorAccent))
        showErrorRV()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val atag = data!!.getStringExtra("tag")
                val code = data.getStringExtra("code")
                when (atag) {
                    "qr_beaker" -> {
                        LayoutEditTextBarcode.setText(etBarcode, code)
                        _reportAnalisaImpl.onGetInfoBarcode(code)
                    }
                }
            }
        }
    }
}