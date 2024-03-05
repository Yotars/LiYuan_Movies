import { ref } from "vue";
import { defineStore } from "pinia";

export const useStore = defineStore("counter", () => {
  const id = ref("");
  const setId = (value: string) => {
    id.value = value;
  };
  return { id, setId };
});
