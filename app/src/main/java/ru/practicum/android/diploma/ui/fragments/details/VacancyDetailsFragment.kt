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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailsBinding
import ru.practicum.android.diploma.domain.models.VacancyDetailModel
import ru.practicum.android.diploma.presentation.model.VacancyDetailScreenState
import ru.practicum.android.diploma.presentation.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.formatSalary
import kotlin.getValue

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

            is VacancyDetailScreenState.Content -> showVacancy(state.vacancy)
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

    private fun showVacancy(vacancy: VacancyDetailModel) = with(binding) {

        containerPlaceholder.isVisible = false
        progressBar.isVisible = false
        toolbar.menu.forEach { item -> item.isVisible = true }
        vacancyAllInfoContainer.isVisible = true

        // Заголовок
        tvTitle.text = vacancy.name

        // Зарплата
        tvSalary.text = formatSalary(
            vacancy.salary?.from,
            vacancy.salary?.to,
            vacancy.salary?.currency
        )

        // Компания
        tvCompanyName.text = vacancy.employer.name
        tvCompanyLocation.text = vacancy.address?.fullAddress ?: vacancy.area.name

        // Опыт
        tvExperienceValue.text = vacancy.experience?.name ?: ""

        // Условия работы (график + тип занятости)
        tvWorkConditions.text = listOfNotNull(
            vacancy.employment?.name,
            vacancy.schedule?.name
        ).joinToString(", ")

        // Описание
        tvWorkDescription.text = vacancy.description

        // Ключевые навыки
        if (vacancy.skills.isNotEmpty()) {
            tvKeySkillsTitle.isVisible = true
            tvKeySkills.isVisible = true
            tvKeySkills.text = vacancy.skills.joinToString(separator = "\n") { "• $it" }
        } else {
            tvKeySkillsTitle.isVisible = false
            tvKeySkills.isVisible = false
        }

        // Контакты
        if (vacancy.contacts != null) {
            tvContactsName.text = vacancy.contacts.name
            tvContactsEmail.text = getString(R.string.email, vacancy.contacts.email)
        } else {
            tvContactsTitle.isVisible = false
            tvContactsName.isVisible = false
        }
        val email = vacancy.contacts?.email
        if (!email.isNullOrEmpty()) {
            tvContactsEmail.setOnClickListener {
                viewModel.onEmailClicked(email)
            }
        }


        val phonesAdapter = PhonesAdapter { phone ->
            viewModel.onCallClicked(phone)
        }
        binding.rvPhones.layoutManager = LinearLayoutManager(requireContext())
        rvPhones.adapter = phonesAdapter
        val phones = vacancy.contacts?.phones.orEmpty()
        if (phones.isEmpty()) {
            binding.rvPhones.isVisible = false
        } else {
            binding.rvPhones.isVisible = true
            phonesAdapter.submitList(phones)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_share -> {
                    viewModel.onShareClicked(vacancy.url)
                    true
                }

                R.id.action_favorite -> {
                    // действие
                    true
                }

                else -> false
            }
        }

        // Логотип компании
        if (vacancy.employer.logo.isNotEmpty()) {
            Glide.with(ivCompanyLogo)
                .load(vacancy.employer.logo)
                .placeholder(R.drawable.company_logo_placeholder)
                .error(R.drawable.company_logo_placeholder)
                .fitCenter()
                .into(ivCompanyLogo)
        }
    }

    companion object {
        private const val ARGS_VACANCY = "vacancyId"

        fun createArgs(vacancyId: String): Bundle =
            bundleOf(ARGS_VACANCY to vacancyId)
    }
}
