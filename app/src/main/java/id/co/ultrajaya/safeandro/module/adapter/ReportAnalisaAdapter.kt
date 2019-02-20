package id.co.ultrajaya.safeandro.module.adapter

import android.content.Context
import android.graphics.Color
import android.net.LinkAddress
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.co.ultrajaya.safeandro.R
import id.co.ultrajaya.safeandro.model.response.general.ItemMaterial
import id.co.ultrajaya.safeandro.module.contract.ReportAnalisaContract
import kotlinx.android.synthetic.main.item_3_coloumn.view.*

class ReportAnalisaAdapter() : RecyclerView.Adapter<ReportAnalisaAdapter.myViewHolder>() {

    lateinit var _reportAnalisaList: ArrayList<ItemMaterial>
    lateinit var _reportAnalisaView: ReportAnalisaContract.View
    lateinit var _context : Context

    constructor(icontext : Context, ireportAnalisaList: ArrayList<ItemMaterial>, ireportAnalisaView: ReportAnalisaContract.View) : this() {
        this._context = icontext
        this._reportAnalisaList = ireportAnalisaList
        this._reportAnalisaView = ireportAnalisaView
    }

    override fun onCreateViewHolder(iparent: ViewGroup, iviewType: Int): ReportAnalisaAdapter.myViewHolder {
        val view: View = LayoutInflater.from(iparent.context).inflate(R.layout.item_3_coloumn, iparent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(iholder: myViewHolder, iposition: Int) {
        val aitemReportAnalisa = _reportAnalisaList[iposition]

        if(iposition == 0){
            iholder.tvLabelKolom1.text = "Tipe Sample"
            iholder.tvLabelKolom2.text = "Nilai"
            iholder.tvLabelKolom3.text = "Standard"
        }else{
            iholder.tvHeader.visibility = View.GONE
        }

        iholder.tvNumber.text = (iposition + 1).toString()
        iholder.tvTipe.text = aitemReportAnalisa.kodeAnalisa
        iholder.tvHasil.text = aitemReportAnalisa.nilai
        iholder.tvStandard.text = aitemReportAnalisa.standard
        if(!aitemReportAnalisa.isAcc!!){
            iholder.llLayoutKontainer.setBackgroundColor(ContextCompat.getColor(_context, R.color.redLight))
            iholder.tvNumber.background = ContextCompat.getDrawable(_context, R.drawable.circle_number_red)
        }
    }

    override fun getItemCount() = _reportAnalisaList.size

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*Header*/
        internal var tvHeader: LinearLayout = itemView.layout_header
        internal var tvLabelKolom1 : TextView = itemView.label1
        internal var tvLabelKolom2 : TextView = itemView.label2
        internal var tvLabelKolom3 : TextView = itemView.label3

        /*Detail*/
        internal var tvNumber: TextView = itemView.tvNumber
        internal var tvTipe: TextView = itemView.tvKolom1
        internal var tvHasil: TextView = itemView.tvKolom2
        internal var tvStandard: TextView = itemView.tvKolom3
        internal var llLayoutKontainer : LinearLayout = itemView.layout_container
    }
}