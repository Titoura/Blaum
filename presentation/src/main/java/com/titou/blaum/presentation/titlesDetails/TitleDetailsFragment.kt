package com.titou.blaum.presentation.titlesDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.titou.blaum.entities.Title
import com.titou.blaum.presentation.R
import com.titou.blaum.presentation.databinding.FragmentTitleDetailsBinding
import com.titou.blaum.presentation.utils.loadWithHeader


class TitleDetailsFragment : Fragment() {

    private companion object {
        private val TAG = "TitleDetailsFragment"
    }

    val args: TitleDetailsFragmentArgs by navArgs()

    private var _binding: FragmentTitleDetailsBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    lateinit var title: Title
    private var actionBar: ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle == null) {
            Log.e(TAG, "TitleDetailsFragment did not receive title data")
            return
        }
        title = TitleDetailsFragmentArgs.fromBundle(bundle).title
        if (savedInstanceState == null) { // fragment was no recreated due to a configuration change
            sharedElementEnterTransition = TransitionSet().apply {
                addTransition(ChangeBounds())
                addTransition(ChangeTransform())
                addTransition(ChangeClipBounds())
                addTransition(ChangeImageTransform())
                addListener(object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) {
                        super.onTransitionEnd(transition)
                        binding.titleAlbumTextview.animate().alpha(1F)
                        binding.titleTextview.animate().alpha(1F)
                    }
                })

            }

        }

        exitTransition = Fade(Fade.OUT)
        enterTransition = Fade(Fade.IN)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTitleDetailsBinding.inflate(layoutInflater)
        binding.title = title
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        setUpUI()

        return binding.root
    }

    fun setUpUI() {
        actionBar?.title = title.title

        postponeEnterTransition()

        Glide
            .with(requireContext())
            .loadWithHeader(title.url)
            .apply(RequestOptions().circleCrop())
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.printStackTrace()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.titleImageview)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = getString(R.string.app_name)

        _binding = null
        super.onDestroy()
    }

}