<template>
  <div class="nodrag">
    <div id="dragbar"></div>
    <div id="frame">
      <router-view/>
    </div>
  </div>
</template>

<style>
#dragbar{
  position: absolute;
  top: 0;
  left: 0;
  -webkit-app-region: drag;
  width: 100%;
  height: 5%;
}
#frame{
  font-family: Arial;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.nodrag {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;
}
</style>
