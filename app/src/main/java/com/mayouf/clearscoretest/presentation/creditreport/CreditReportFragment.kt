package com.mayouf.clearscoretest.presentation.creditreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.futured.donut.DonutSection
import com.mayouf.clearscoretest.R
import com.mayouf.clearscoretest.databinding.FragmentCreditReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditReportFragment : Fragment() {
    private var fragmentCreditReportBinding: FragmentCreditReportBinding? = null
    private val viewModel: CreditReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCreditReport()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCreditReportBinding =
            FragmentCreditReportBinding.inflate(inflater, container, false)

        viewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                fragmentCreditReportBinding!!.progressBar.visibility = View.VISIBLE
                fragmentCreditReportBinding!!.donutScoreLabel.visibility = View.GONE
                fragmentCreditReportBinding!!.donutMaxScoreValue.visibility = View.GONE
                fragmentCreditReportBinding!!.donutCreditScoreValue.visibility = View.GONE
                fragmentCreditReportBinding!!.errorMessage.visibility = View.GONE
            } else {
                fragmentCreditReportBinding!!.progressBar.visibility = View.INVISIBLE
                fragmentCreditReportBinding!!.donutScoreLabel.visibility = View.VISIBLE
                fragmentCreditReportBinding!!.donutMaxScoreValue.visibility = View.VISIBLE
                fragmentCreditReportBinding!!.donutCreditScoreValue.visibility = View.VISIBLE
                fragmentCreditReportBinding!!.errorMessage.visibility = View.GONE
            }

        })

        viewModel.creditLiveData.observe(viewLifecycleOwner, { creditReport ->
            val donutSection = DonutSection(
                name = "creditSection",
                color = R.color.black,
                amount = creditReport.creditReportInfo.score.toFloat()
            )
            fragmentCreditReportBinding!!.donutCreditScoreValue.text =
                creditReport.creditReportInfo.score.toString()
            ("out of " + creditReport.creditReportInfo.maxScoreValue.toString()).also {
                fragmentCreditReportBinding!!.donutMaxScoreValue.text = it
            }
            fragmentCreditReportBinding!!.donutView.cap =
                creditReport.creditReportInfo.maxScoreValue.toFloat()
            fragmentCreditReportBinding!!.donutView.submitData(listOf(donutSection))
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            fragmentCreditReportBinding!!.progressBar.visibility = View.INVISIBLE
            fragmentCreditReportBinding!!.donutScoreLabel.visibility = View.GONE
            fragmentCreditReportBinding!!.donutMaxScoreValue.visibility = View.GONE
            fragmentCreditReportBinding!!.donutCreditScoreValue.visibility = View.GONE
            fragmentCreditReportBinding!!.errorMessage.visibility = View.VISIBLE
        })
        return fragmentCreditReportBinding!!.root

    }

    companion object {

        val FRAGMENT_NAME: String = CreditReportFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            CreditReportFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}