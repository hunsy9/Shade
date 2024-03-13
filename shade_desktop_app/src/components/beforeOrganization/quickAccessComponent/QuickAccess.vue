<template>
  <div class="quickAccessWrapper">
    Quick Access
    <div class="quickAccessItemBox">
    My Local Terminal
      <button>
        Connect
      </button>
    </div>
  </div>

</template>

<script>
export default {
  name: "QuickAccess"
}
</script>

<style scoped>
.quickAccessWrapper{
  margin-left: 3rem;
  margin-top: 1.5rem;
}
.quickAccessItemBox{
  margin-top: 20px;
  background-color: #323232;
  animation: fadeInUp 1s ease backwards;
  border-radius: 0.4rem;
  padding: 1rem 0;
  padding-left: 2rem;
  width: 40%;
}
button{
  float: right;
  background-color: #636363c2;
  border-radius: 0.2rem;
  border: none;
  box-shadow: 0 1px 1px 0.5px #0000002f;
  cursor:pointer;
  color: white;
  padding: 0.1rem 1.2rem;
  margin-right: 0.7rem;
}
@keyframes fadeInUp{
  0%{transform:translate(0px, 7px); opacity: 0;}
  100%{transform:translate(0px, 0); opacity: 1;}
}
</style>