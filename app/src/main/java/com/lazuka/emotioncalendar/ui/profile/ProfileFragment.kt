package com.lazuka.emotioncalendar.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lazuka.emotioncalendar.R
import com.lazuka.emotioncalendar.databinding.FragmentProfileBinding
import com.lazuka.emotioncalendar.domain.model.Personality
import com.lazuka.emotioncalendar.domain.model.Sex
import com.lazuka.emotioncalendar.domain.model.UserModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        initAuthState()
        initProfileState()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAuthState() = with(binding) {
        etEnterId.doAfterTextChanged { text ->
            btnLogin.isEnabled = text?.toString().orEmpty().isNotBlank()
            viewModel.onIdChanged(text?.toString().orEmpty())
        }

        tvReg.setOnClickListener {
            viewModel.register()
        }

        btnLogin.setOnClickListener {
            viewModel.login()
        }
    }

    private fun initProfileState() = with(binding) {
        tvLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                isAuthorizedFlow.collectLatest(::showAuthState)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userFlow.collectLatest(::showProfile)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                errorFlow.collectLatest(::showError)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                regSuccessFlow.collectLatest { user ->
                    showProfile(user)
                    showRegisterSuccess()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                fillSuccessFlow.collectLatest {
                    showFillSuccess()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navigateToMainFlow.collectLatest {
                    findNavController().navigate(R.id.actionToMain)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                authorizedUserIdFlow.collectLatest { showRegisterSuccess() }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                isLoading.collectLatest { visible -> binding.progress.isVisible = visible }
            }
        }
    }

    private fun showAuthState(isAuthorized: Boolean) = with(binding) {
        groupProfile.isVisible = isAuthorized
        groupAuth.isVisible = !isAuthorized
    }

    private fun showProfile(user: UserModel) = with(binding) {
        tvUserId.text = user.userId.toString()
        switchAdult.isChecked = user.adult
        when (user.personality) {
            Personality.EXTROVERT -> rbExtravert.isChecked = true
            Personality.INTROVERT -> rbIntrovert.isChecked = true
            else -> Unit
        }
        when (user.sex) {
            Sex.WOMAN -> rbFemale.isChecked = true
            Sex.MAN -> rbMale.isChecked = true
            else -> Unit
        }
        viewFilled.isVisible = user.hasFilled

        if (!user.hasFilled) {
            switchAdult.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onAdultChecked(isChecked)
            }

            rgPersonality.setOnCheckedChangeListener { _, checkedId ->
                val personality = when(checkedId) {
                    rbExtravert.id -> Personality.EXTROVERT
                    rbIntrovert.id -> Personality.INTROVERT
                    else -> Personality.NOT_FILLED
                }
                viewModel.onPersonalityChanged(personality)
            }

            rgSex.setOnCheckedChangeListener { _, checkedId ->
                val sex = when(checkedId) {
                    rbMale.id -> Sex.MAN
                    rbFemale.id -> Sex.WOMAN
                    else -> Sex.NOT_FILLED
                }
                viewModel.onSexChanged(sex)
            }

            btnSave.isVisible = true
            btnSave.setOnClickListener {
                viewModel.onSaveClicked()
            }
        }
    }

    private fun showError(@StringRes errorRes: Int) {
        Toast.makeText(requireContext(), getString(errorRes), Toast.LENGTH_LONG).show()
    }

    private fun showRegisterSuccess() {
        Toast.makeText(requireContext(), getString(R.string.register_success), Toast.LENGTH_SHORT).show()
    }

    private fun showFillSuccess() {
        Toast.makeText(requireContext(), getString(R.string.fill_success), Toast.LENGTH_SHORT).show()
    }
}