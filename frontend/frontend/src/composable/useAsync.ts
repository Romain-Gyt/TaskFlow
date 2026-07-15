import {type DeepReadonly, readonly, ref, type Ref} from "vue";

interface UseAsyncResult<T, Args extends any[]> {
  data: DeepReadonly<Ref<T | null>>;
  error: DeepReadonly<Ref<String | null>>;
  loading: DeepReadonly<Ref<boolean>>;
  execute: (...args: Args) => Promise<T | null>;
}

export  function useAsync <T,  Args extends any[]>(
  asyncFn: (...args: Args) => Promise<T>,
): UseAsyncResult<T, Args>{
  const data = ref<T | null>(null) as Ref<T | null>;
  const error = ref<string | null>(null);
  const loading = ref<boolean>(false);

  async function execute (...args: Args): Promise<T | null> {
    loading.value = true;
    error.value = null;

    try{
      const result = await asyncFn(...args);
      data.value = result;
      return result;
    }catch(err: any){
      error.value = err.message || "Something went wrong";
      data.value = null;
      return null;
    }finally {
      loading.value = false;
    }
  }

  return {
    data: readonly(data),
    error: readonly(error),
    loading: readonly(loading),
    execute,
  }
}
