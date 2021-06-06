package ru.tinkoff.moshkovtinkofftest.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tinkoff.data.model.Gif
import ru.tinkoff.moshkovtinkofftest.R
import ru.tinkoff.moshkovtinkofftest.databinding.FragmentMainBinding


class PlaceholderFragment : Fragment() {

    private val viewModel: PageViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding

	companion object {

		private const val API_PATH = "api_path"
		private const val SHARED_PREFERENCES_FILE_NAME = "Moshkov_tinkoff_test_shared_preferences"
		private const val GIFS_PAGE = "gifs_page"
		private const val GIF_NUMBER = "gif_number"

		private enum class Error {
			INTERNET_ERROR,
			NOTHING_ERROR
		}

		@JvmStatic
		fun newInstance(path: String): PlaceholderFragment {
			return PlaceholderFragment().apply {
				arguments = Bundle().apply { putString(API_PATH, path) }
			}
		}
	}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

	    viewModel.setPath(arguments?.getString(API_PATH)!!)
    }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentMainBinding.inflate(inflater, container, false)

		viewModel.gifsPage = requireContext()
			.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
			.getInt("$GIFS_PAGE-$API_PATH", 0)

		viewModel.gifNumber = requireContext()
			.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
			.getInt("$GIF_NUMBER-$API_PATH", 0)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(binding) {

			setErrorVisibility(View.GONE)
			loading.visibility = View.VISIBLE
			if (viewModel.gifNumber == 0 && viewModel.gifsPage == 0) {
				ibPreviousButton.isEnabled = false
				ibPreviousButton.alpha = 0.5f
				ibPreviousButton.background.alpha = 50
			}

			if (checkInternet()) {
				viewModel.requestGifs {
					loading.visibility = View.GONE
					showError(Error.NOTHING_ERROR)
				}
			} else
				showError(Error.INTERNET_ERROR)

			buttonReload.setOnClickListener {
				if (checkInternet()) {
					viewModel.requestGifs { showError(Error.NOTHING_ERROR) }
					setErrorVisibility(View.GONE)
					setMainVisibility(View.VISIBLE)
				}
			}

			viewModel.gifsList.observe(viewLifecycleOwner) {
				if (it.isEmpty()) {
					loading.visibility = View.GONE
					showError(Error.NOTHING_ERROR)
					return@observe
				}

				with(viewModel) {
					gifs.clear()
					gifs.addAll(it)
					if (viewModel.gifNumber < 0)
						viewModel.gifNumber = gifs.lastIndex
					glideLoad(gifs[viewModel.gifNumber])
				}
			}

			ibPreviousButton.setOnClickListener {
				loading.visibility = View.VISIBLE
				gifDescription.text = ""
				with(viewModel) {
					gifNumber--
					when {
						gifNumber == 0 && gifsPage == 0 -> {
							it.isEnabled = false
							it.alpha = 0.5f
							glideLoad(gifs[gifNumber])
						}
						gifNumber < 0 -> {
							gifsPage--
							requestGifs { showError(Error.NOTHING_ERROR) }
						}
						else -> {
							loading.visibility = View.VISIBLE
							glideLoad(gifs[gifNumber])
						}
					}
				}
			}

			ibNextButton.setOnClickListener {
				loading.visibility = View.VISIBLE
				gifDescription.text = ""
				with(viewModel) {
					gifNumber++
					ibPreviousButton.isEnabled = true
					ibPreviousButton.alpha = 1.0f
					if (gifNumber > gifs.lastIndex) {
						gifNumber = 0
						gifsPage++
						requestGifs { showError(Error.NOTHING_ERROR) }
					} else
						glideLoad(gifs[gifNumber])
				}
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()

		with(requireContext().getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit()) {
			putInt("$GIFS_PAGE-$API_PATH", viewModel.gifsPage)
			putInt("$GIF_NUMBER-$API_PATH", viewModel.gifNumber)
			apply()
		}
	}

	private fun showError(error: Error) = with(binding) {
		setMainVisibility(View.GONE)
		setErrorVisibility(View.VISIBLE)

		when(error) {
			Error.INTERNET_ERROR -> {
				ivError.setImageResource(R.drawable.ic_no_network)
				tvError.text = getString(R.string.internet_error)
			}
			Error.NOTHING_ERROR -> {
				ivError.setImageResource(R.drawable.ic_nothing_here)
				tvError.text = getString(R.string.empty_error)
			}
		}
	}

	private fun checkInternet(): Boolean {
		val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		return cm.activeNetworkInfo?.isConnected ?: false
	}

	private fun glideLoad(gif: Gif) = Glide.with(requireContext())
		.asGif()
		.listener(object : RequestListener<GifDrawable> {
			override fun onLoadFailed(
				e: GlideException?,
				model: Any?,
				target: Target<GifDrawable>?,
				isFirstResource: Boolean
			): Boolean {
				binding.loading.visibility = View.GONE
				showError(Error.INTERNET_ERROR)
				return false
			}

			override fun onResourceReady(
				resource: GifDrawable?,
				model: Any?,
				target: Target<GifDrawable>?,
				dataSource: DataSource?,
				isFirstResource: Boolean
			): Boolean {
				binding.gifDescription.text = gif.description
				binding.loading.visibility = View.GONE
				return false
			}

		})
		.centerInside()
		.diskCacheStrategy(DiskCacheStrategy.ALL)
		.load(gif.gifUrl)
		.into(binding.ivImage)

	private fun setErrorVisibility(visibility: Int) = with(binding) {
		ivError.visibility = visibility
		tvError.visibility = visibility
		buttonReload.visibility = visibility
	}

	private fun setMainVisibility(visibility: Int) = with(binding) {
		ivImage.visibility = visibility
		ibNextButton.visibility = visibility
		ibPreviousButton.visibility = visibility
	}
}