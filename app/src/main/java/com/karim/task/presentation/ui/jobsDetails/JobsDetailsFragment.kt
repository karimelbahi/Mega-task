package com.karim.task.presentation.ui.jobsDetails

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.karim.task.R
import com.karim.task.databinding.FragmentPostListDetailsBinding
import com.karim.task.presentation.ui.jobs.JobsModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JobsDetailsFragment : Fragment() {

    private val viewModel by viewModels<JobsModel>()
    private lateinit var binding: FragmentPostListDetailsBinding
    private val args: JobsDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPostListDetailsBinding>(
            inflater,
            R.layout.fragment_post_list_details,
            container,
            false
        ).apply {

            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.postId.favorite) {
            binding.favImg.setImageResource(R.drawable.star)
        } else {
            binding.favImg.setImageResource(R.drawable.not_star)
        }

        binding.favImg.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                if (args.postId.favorite) {
                    binding.favImg.setImageResource(R.drawable.not_star)
                } else {
                    binding.favImg.setImageResource(R.drawable.star)
                }
                args.postId.favorite = !args.postId.favorite
                viewModel.updateFavorite(args.postId)
            }

        }


        if (args.postId.company_logo.isNullOrEmpty())
            Picasso.get().load(R.drawable.ic_place_holder)
                .into(binding.imagePost);
        else
            Picasso.get().load(args.postId.company_logo)
                .into(binding.imagePost);

        binding.textCompany.text = textStyle(
            0,
            7,
            "COMPANY : " + args.postId.company + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textTitle.text = textStyle(
            0,
            5,
            "TITLE : " + args.postId.title + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textType.text = textStyle(
            0,
            4,
            "TYPE : " + args.postId.type + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.textCompanyUrl.text = textStyle(
            0,
            11,
            "COMPANY URL : " + args.postId.company_url + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textCreatedAt.text = textStyle(
            0,
            10,
            "CREATED AT : " + args.postId.created_at + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textLocatin.text = textStyle(
            0,
            8,
            "LOCATION : " + args.postId.location + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textDescription.text = textStyle(
            0,
            11,
            "DESCRIPTION : " + args.postId.description + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.textDescription.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(
                    "<b>DESCRIPTION</b> : ${args.postId.description}",
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                Html.fromHtml("<b>DESCRIPTION</b> : ${args.postId.description}")
            }

        binding.textHowToApply.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(
                "<b>HOW TO APPLY </b>: ${args.postId.how_to_apply}",
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            Html.fromHtml("<b>HOW TO APPLY </b>: ${args.postId.how_to_apply}")
        }

        binding.textUrl.text = textStyle(
            0,
            3,
            "URL : " + args.postId.url + ".",
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }

    private fun textStyle(start: Int, end: Int, str: String, style: Int): SpannableString {
        val ss = SpannableString(str)
        ss.setSpan(StyleSpan(Typeface.BOLD), start, end, style)
        return ss
    }

}