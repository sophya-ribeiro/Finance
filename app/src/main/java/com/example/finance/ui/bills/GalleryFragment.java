//package com.example.finance.ui.bills;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.finance.databinding.FragmentBillBinding;
//import com.example.finance.databinding.FragmentGalleryBinding;
//
//public class BillsFragment extends Fragment {
//
//    private FragmentBillBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        Fragment galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
//
//        binding = FragmentBillBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}