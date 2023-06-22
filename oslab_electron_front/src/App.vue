<template>
  <div id="dragbar"></div>
  <div id="frame">
    <router-view/>
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
</style>
