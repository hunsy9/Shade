<template>
  <div class="modal">
    <div class="overlay">
      <div class="modal-card">
        <div class="categoryNameWrapper">
          <label class="categoryName"> Category Name </label>
          <input type="text"/>
        </div>
        <div class="btnWrapper">
          <button @click="$emit('closeAddCategoryModal')" class="cancel">Cancel</button>
          <button> Save</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ModalAddCategory"
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
  min-height: 230px;
  margin: auto;
  margin-top: 270px;
  padding: 20px;
  background-color: white;
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

input {
  font-size: 0.9rem;
  color: white;
  padding: 0.2rem 0;
  text-align: center;
  background-color: #565656;
  width: 17rem;
  border: none;
  outline: none;
}
</style>