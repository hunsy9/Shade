<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="categoryNameWrapper">{{ headerName }}</div>
        <div class="categoryNameWrapper">
          <label class="categoryName"> Category Name </label>
          <input type="text" v-model="categoryName" v-bind:placeholder="editValue"/>
        </div>
        <div class="btnWrapper">
          <button @click="$emit('closeAddCategoryModal')" class="cancel">Cancel</button>
          <button @click="addCategoryStateController"> Save</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ActionCategoryState} from "@/store/inOrganization";
import {mapActions} from "vuex";

export default {
  name: "ModalAddCategory",
  props: {
    ActionCategoryState: ActionCategoryState,
    ActionCategoryDto: Object
  },
  data() {
    return {
      categoryName: "",
      headerName: "",
      editValue: ""
    }
  },
  created() {
    if (this.ActionCategoryState == ActionCategoryState.ADDL1 || this.ActionCategoryState == ActionCategoryState.ADDL2) {
      this.headerName = "Add New Category"
    } else {
      this.headerName = "Edit Category"
      this.editValue = this.ActionCategoryDto.editValue
    }
  },
  methods: {
    ...mapActions("inOrganization", ["addCategoryL1", "addCategoryL2", "reFetchCategory", "editCategory", "reFetchSelectedCategory"]),
    async addCategoryStateController() {
      switch (this.ActionCategoryState) {
        case ActionCategoryState.ADDL1:
          await this.addCategoryL1({
            "org_id": this.ActionCategoryDto.org_id,
            "project_id": this.ActionCategoryDto.project_id,
            "prev": this.categoryName
          })
          await this.reFetchCategory()
          this.$emit('closeAddCategoryModal')
          break
        case ActionCategoryState.ADDL2:
          await this.addCategoryL2({
            "category_id": this.ActionCategoryDto.category_id,
            "next": this.categoryName
          })
          await this.reFetchCategory()
          this.$emit('closeAddCategoryModal')
          break
        case ActionCategoryState.EDIT:
          await this.editCategory({
            categoryKeys: this.ActionCategoryDto.categoryKeys,
            prev: this.ActionCategoryDto.type == "L1" ? this.categoryName : "",
            next: this.ActionCategoryDto.type == "L2" ? this.categoryName : "",
            type: this.ActionCategoryDto.type
          })
          await this.reFetchCategory()
          await this.reFetchSelectedCategory({
            prev: this.ActionCategoryDto.type == "L1" ? this.categoryName : "",
            next: this.ActionCategoryDto.type == "L2" ? this.categoryName : "",
          })
          this.$emit('closeAddCategoryModal')
          break
        default:
          break
      }
    }
  }
}
</script>

<style scoped>
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}

.modal-card {
  position: relative;
  max-width: 475px;
  min-height: 100px;
  margin: auto;
  margin-top: 270px;
  padding: 20px;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow: 0.2px 0.2px 4px 4px #0000002f;
  animation: fade-in-down 0.5s;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 0.2px 1px 1px #0000002f;
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}

/*.categoryNameWrapper {*/
/*  text-align: center;*/
/*  margin-top: 3px;*/
/*  margin-bottom: 3px;*/
/*}*/
.categoryNameWrapper {
  text-align: center;
  justify-content: center;
  display: flex;
  color: white;
  margin-bottom: 30px;
  margin-top: 15px;
}

.categoryName {
  font-size: 0.8rem;
  padding-right: 2rem;
  color: white;
}

input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #2f2f2f;
  width: 17rem;
  border: none;
  outline: none;
}

.btnWrapper {
  width: 100%;
  display: flex;
  text-align: center;
  justify-content: space-between;
  position: relative;
}

@keyframes fade-in-down {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>