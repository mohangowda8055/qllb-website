import { useQuery } from "@tanstack/react-query";
import customFetch from "../../axios/custom";
import { useDispatch } from "react-redux";
import {
  setCommercialVProductList,
  setFourVProductList,
  setInverterBatteryProductList,
  setInverterProductList,
  setThreeVProductList,
  setTwoVProductList,
} from "../../features/product/productSlice";

export const useFetchModels = (productType, brandId) => {
  const {
    isLoading: isModelsLoading,
    data: models,
    error,
    isError,
    refetch: modelsRefetch,
  } = useQuery({
    queryKey: ["models", productType, brandId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `/${productType}/brands/${brandId}/models`
      );
      return data?.data || null;
    },
    enabled: false,
  });
  if (isError) {
    throw new Error("Api request failed");
  }
  return { isModelsLoading, models, modelsRefetch, error, isError };
};

export const useFetchSegments = (brandId) => {
  const {
    isLoading: isSegmentsLoading,
    data: segments,
    error,
    isError,
    refetch: segmentsRefetch,
  } = useQuery({
    queryKey: ["segments", brandId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `/commercialv/brands/${brandId}/segments`
      );
      return data?.data || null;
    },
    enabled: false,
  });
  if (isError) {
    throw new Error("Api request failed");
  }
  return { isSegmentsLoading, segments, segmentsRefetch, error, isError };
};

export const useFetchWarranties = () => {
  const {
    isLoading: isWarrantiesLoading,
    data: warranties,
    error,
    isError,
  } = useQuery({
    queryKey: ["warranties"],
    queryFn: async () => {
      const { data } = await customFetch.get(`/inverterbattery/warranties`);
      return data?.data || null;
    },
  });
  if (isError) {
    throw new Error("Api request failed");
  }
  return { isWarrantiesLoading, warranties, error, isError };
};

export const useFetchCommercialModels = (brandId, segmentId) => {
  const {
    isLoading: isCommerialModelsLoading,
    data: commercialModels,
    error,
    isError,
    refetch: commercialModelsRefetch,
  } = useQuery({
    queryKey: ["commercialmodels", brandId, segmentId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `/commercialv/brands/${brandId}/segments/${segmentId}/models`
      );
      return data?.data || null;
    },
    enabled: false,
  });
  if (isError) {
    throw new Error("Api request failed");
  }
  return {
    isCommerialModelsLoading,
    commercialModels,
    commercialModelsRefetch,
    error,
    isError,
  };
};

export const useFetchBatteries = (productType, modelId, fuelTypeId) => {
  const dispatch = useDispatch();
  const {
    isLoading: isBatteriesLoading,
    data: batteries,
    isError: isBatteriesError,
    refetch: batteriesRefetch,
  } = useQuery({
    queryKey: ["batteries", productType, modelId, fuelTypeId],
    queryFn: async () => {
      if (productType === "threev") {
        const { data } = await customFetch.get(
          `/${productType}/models/${modelId}/fueltypes/${fuelTypeId}/batteries`
        );
        dispatch(setThreeVProductList(data?.data));
        return data?.data;
      } else if (productType === "fourv") {
        const { data } = await customFetch.get(
          `/${productType}/models/${modelId}/fueltypes/${fuelTypeId}/batteries`
        );
        dispatch(setFourVProductList(data?.data));
        return data?.data;
      } else {
        const { data } = await customFetch.get(
          `/${productType}/models/${modelId}/batteries`
        );
        productType === "twov" && dispatch(setTwoVProductList(data?.data));
        productType === "commercialv" &&
          dispatch(setCommercialVProductList(data?.data));
        return data?.data;
      }
    },
    enabled: false,
  });
  return { isBatteriesLoading, isBatteriesError, batteries, batteriesRefetch };
};

export const useFetchInverterBatteries = (backupDurationId, warrantyId) => {
  const dispatch = useDispatch();
  const {
    isLoading: isInverterBatteriesLoading,
    data: inverterBatteries,
    isError: isInverterBatteriesError,
    refetch: inverterBatteriesRefetch,
  } = useQuery({
    queryKey: ["inverterbatteries", backupDurationId, warrantyId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `/inverterbattery/backupdurations/${backupDurationId}/warranties/${warrantyId}/batteries`
      );
      dispatch(setInverterBatteryProductList(data?.data));
      return data?.data;
    },
    enabled: false,
  });
  return {
    isInverterBatteriesLoading,
    isInverterBatteriesError,
    inverterBatteries,
    inverterBatteriesRefetch,
  };
};

export const useFetchInverters = (capacityId) => {
  const dispatch = useDispatch();
  const {
    isLoading: isInvertersLoading,
    data: inverters,
    isError: isInvertersError,
    refetch: invertersRefetch,
  } = useQuery({
    queryKey: ["inverters", capacityId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `/inverter/capacities/${capacityId}/inverters`
      );
      dispatch(setInverterProductList(data?.data));
      return data?.data;
    },
    enabled: false,
  });
  return { isInvertersLoading, isInvertersError, inverters, invertersRefetch };
};

export const useFetchFuels = (productType, modelId) => {
  const {
    isLoading: isFuelsLoading,
    data: fuels,
    isError: isFuelsError,
    refetch: fuelsRefetch,
  } = useQuery({
    queryKey: ["fuels", productType, modelId],
    queryFn: async () => {
      const { data } = await customFetch.get(
        `fueltype/${productType}/${modelId}/fueltypes`
      );
      return data?.data;
    },
    enabled: false,
  });
  return { isFuelsLoading, isFuelsError, fuels, fuelsRefetch };
};
