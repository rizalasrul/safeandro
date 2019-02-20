package id.co.ultrajaya.safeandro.module.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.util.AlertDialogCustom
import id.co.ultrajaya.safeandro.module.contract.PindahBeakerContract
import kotlinx.android.synthetic.main.item_barcode.view.*

class PindahBeakerAdapter() : RecyclerView.Adapter<PindahBeakerAdapter.myViewHolder>() {

    lateinit var _barcodeList: ArrayList<String>
    lateinit var _alertDialog: AlertDialogCustom
    lateinit var _pindahBeakerView: PindahBeakerContract.View

    constructor(ibarcodeList: ArrayList<String>, ipindahBeakerView: PindahBeakerContract.View) : this() {
        this._barcodeList = ibarcodeList
        this._pindahBeakerView = ipindahBeakerView
    }

    override fun onCreateViewHolder(iparent: ViewGroup, iviewType: Int): PindahBeakerAdapter.myViewHolder {
        val view: View = LayoutInflater.from(iparent.context).inflate(R.layout.item_barcode, iparent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(iholder: myViewHolder, iposition: Int) {
        val abarcode = _barcodeList[iposition]

        iholder.tvNumber.text = (iposition + 1).toString()
        iholder.tvBarcode.text = abarcode
        iholder.ivDelete.setOnClickListener {
            _pindahBeakerView.showAlertDialogWithOptions("Apakah yakin menghapus ?", iposition)
        }
    }

    override fun getItemCount() = _barcodeList.size

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvNumber: TextView = itemView.tvNumber
        internal var tvBarcode: TextView = itemView.tvBarcode
        internal var ivDelete: ImageView = itemView.ivDelete
    }
}