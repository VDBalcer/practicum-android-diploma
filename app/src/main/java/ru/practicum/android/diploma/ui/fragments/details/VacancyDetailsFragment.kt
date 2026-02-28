package ru.practicum.android.diploma.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyDetailScreenState
import ru.practicum.android.diploma.presentation.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.Converter
import ru.practicum.android.diploma.util.SalaryFormater

class VacancyDetailsFragment : Fragment() {
    private var _binding: FragmentVacancyDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel.observeMainState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: VacancyDetailScreenState) {
        when (state) {
            VacancyDetailScreenState.Loading -> loadInfo()

            VacancyDetailScreenState.JobNotFound -> showError(
                R.drawable.not_fount_vacancy,
                getString(R.string.not_fount_vacancy_info_message)
            )

            VacancyDetailScreenState.ServerError -> showError(
                R.drawable.server_error_vacancy,
                getString(R.string.title_server_error)
            )

            is VacancyDetailScreenState.Content -> showVacancy(state.vacancy, state.isFavorite)
        }
    }

    private fun loadInfo() {
        binding.apply {
            containerPlaceholder.isVisible = false
            progressBar.isVisible = true
            toolbar.menu.forEach { item -> item.isVisible = false }
            vacancyAllInfoContainer.isVisible = false
        }
    }

    private fun showError(im: Int, message: String) {
        binding.apply {
            containerPlaceholder.isVisible = true
            placeholderImage.setImageResource(im)
            placeholderMessage.text = message
            toolbar.menu.forEach { item -> item.isVisible = false }
            progressBar.isVisible = false
            vacancyAllInfoContainer.isVisible = false
        }
    }

    private fun showVacancy(vacancy: VacancyDetailModel, isFavorite: Boolean) = with(binding) {
        showContentState()

        bindHeader(vacancy)
        bindSalary(vacancy)
        bindCompany(vacancy)
        bindExperience(vacancy)
        bindWorkConditions(vacancy)
        bindDescription(vacancy)
        bindSkills(vacancy)
        bindContacts(vacancy)
        bindPhones(vacancy)
        bindToolbar(vacancy)
        bindLogo(vacancy)
        bindFavoriteIcon(isFavorite)
    }

    private fun showContentState() = with(binding) {
        containerPlaceholder.isVisible = false
        progressBar.isVisible = false
        vacancyAllInfoContainer.isVisible = true
        toolbar.menu.forEach { it.isVisible = true }
    }

    private fun bindHeader(vacancy: VacancyDetailModel) {
        binding.tvTitle.text = vacancy.name
    }

    private fun bindSalary(vacancy: VacancyDetailModel) {
        binding.tvSalary.text = SalaryFormater.formatSalary(
            vacancy.salary?.from,
            vacancy.salary?.to,
            vacancy.salary?.currency
        )
    }

    private fun bindCompany(vacancy: VacancyDetailModel) {
        binding.tvCompanyName.text = vacancy.employer.name
        binding.tvCompanyLocation.text =
            vacancy.address?.fullAddress ?: vacancy.area.name
    }

    private fun bindExperience(vacancy: VacancyDetailModel) {
        binding.tvExperienceValue.text = vacancy.experience?.name.orEmpty()
    }

    private fun bindWorkConditions(vacancy: VacancyDetailModel) {
        binding.tvWorkConditions.text = listOfNotNull(
            vacancy.employment?.name,
            vacancy.schedule?.name
        ).joinToString(", ")
    }

    private fun bindDescription(vacancy: VacancyDetailModel) {
        binding.tvWorkDescription.text = vacancy.description
    }

    private fun bindSkills(vacancy: VacancyDetailModel) {
        val hasSkills = vacancy.skills.isNotEmpty()

        binding.tvKeySkillsTitle.isVisible = hasSkills
        binding.tvKeySkills.isVisible = hasSkills

        if (hasSkills) {
            binding.tvKeySkills.text =
                vacancy.skills.joinToString("\n") { "• $it" }
        }
    }

    private fun bindContacts(vacancy: VacancyDetailModel) {
        val contacts = vacancy.contacts
        val hasContacts = contacts != null

        binding.tvContactsTitle.isVisible = hasContacts
        binding.tvContactsName.isVisible = hasContacts
        binding.tvContactsEmail.isVisible = hasContacts

        if (hasContacts) {
            binding.tvContactsName.text = contacts!!.name

            val email = contacts.email
            binding.tvContactsEmail.text =
                getString(R.string.email, email)

            if (email.isNotEmpty()) {
                binding.tvContactsEmail.setOnClickListener {
                    viewModel.onEmailClicked(email)
                }
            }
        }
    }

    private fun bindPhones(vacancy: VacancyDetailModel) {
        val phones = vacancy.contacts?.phones.orEmpty()
        val hasPhones = phones.isNotEmpty()

        binding.rvPhones.isVisible = hasPhones
        if (!hasPhones) return

        val recyclerView = binding.rvPhones

        // LayoutManager ставим только если его нет
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext())
        }

        // Пытаемся переиспользовать адаптер
        val existingAdapter = recyclerView.adapter as? PhonesAdapter

        if (existingAdapter != null) {
            existingAdapter.submitList(phones)
        } else {
            val newAdapter = PhonesAdapter { phone ->
                viewModel.onCallClicked(phone)
            }
            recyclerView.adapter = newAdapter
            newAdapter.submitList(phones)
        }
    }

    private fun bindToolbar(vacancy: VacancyDetailModel) {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_share -> {
                    viewModel.onShareClicked(vacancy.url)
                    true
                }

                R.id.action_favorite -> {
                    viewModel.onFavoriteClick()
                    true
                }

                else -> false
            }
        }
    }

    private fun bindLogo(vacancy: VacancyDetailModel) {
        val logo = vacancy.employer.logo

        Glide.with(this)
            .load(logo)
            .placeholder(R.drawable.company_logo_placeholder)
            .error(R.drawable.company_logo_placeholder)
            .fitCenter()
            .transform(
                RoundedCorners(
                    Converter.dpToPx(
                        binding.root.resources.getInteger(
                            R.integer.info_company_radius_int
                        ).toFloat(),
                        binding.root
                    )
                )
            )
            .into(binding.ivCompanyLogo)
    }

    private fun bindFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.toolbar.menu.findItem(R.id.action_favorite).setIcon(R.drawable.favorites_24px)
        } else {
            binding.toolbar.menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        private const val ARGS_VACANCY = "vacancyId"

        fun createArgs(vacancyId: String): Bundle =
            bundleOf(ARGS_VACANCY to vacancyId)
    }
}
