<template>
  <div class="zidx">
    <div class="modal">
      <div class="overlay">
        <div :class="getTheme ? 'modal-card' : 'modal-card-light'">
          <div class="headerBar">
            <div :class="getTheme  ? 'settingsNameWrapper' : 'settingsNameWrapper-light'">Settings</div>
            <img src="@/assets/x.png" @click="$emit('closeSettingsModal')" class="x"/>
          </div>
          <div :class="getTheme  ? 'tabBar' : 'tabBar-light'">
            <div class="tab" :class="{'tab-clicked': tab1Clicked == true}" @click="tabClick(1)">Themes</div>
            <div class="tab" :class="{'tab-clicked': tab2Clicked == true}" @click="tabClick(2)">Other</div>
            <div class="tab" :class="{'tab-clicked': tab3Clicked == true}" @click="tabClick(3)">About</div>
          </div>
          <div class="checkboxWrapper">
            <div class="checkbox" :class="{'checkbox-clicked':checkBox1Clicked == true}" @click="checkBoxClick(1)">
              <img class="modeImage" src="@/assets/userDefault.png"/>
              <div class="modeDesc">System Default</div>
            </div>
            <div class="checkbox" :class="{'checkbox-clicked':checkBox2Clicked == true}" @click="checkBoxClick(2)">
              <img class="modeImage" src="@/assets/lightMode.png"/>
              <div class="modeDesc">Light Mode</div>
            </div>
            <div class="checkbox" :class="{'checkbox-clicked':checkBox3Clicked == true}" @click="checkBoxClick(3)">
              <img class="modeImage" src="@/assets/darkMode.png"/>
              <div class="modeDesc">Dark Mode</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {themeState} from "@/store/inOrganization";
import {mapMutations, mapGetters} from "vuex";

export default {
  name: "ModalSettings",
  data(){
    return{
      tab1Clicked: true,
      tab2Clicked: false,
      tab3Clicked: false,
      checkBox1Clicked: false,
      checkBox2Clicked: false,
      checkBox3Clicked: false,
    }
  },
  computed:{
    ...mapGetters('inOrganization', ['getTheme']),
  },
  methods:{
    ...mapMutations('inOrganization',['setTheme']),
    setThemeLight(){
      this.setTheme(themeState.LIGHT)
      let theme = document.querySelector(':root');
      theme.style.setProperty('--ModeTextColor', 'black');
      theme.style.setProperty('--ModeModalBackgroundColor','white')
      theme.style.setProperty('--ModeBtnColor','#F0F0F0')
      theme.style.setProperty('--ModeInputColor','#eaeaea')
      theme.style.setProperty('--ModeInputInnerTextColor', '#5F5F5F')
      theme.style.setProperty('--ModeLevel1Color','#f8f8f8')
      theme.style.setProperty('--ModeLevel2Color','#f1f1f1')
      theme.style.setProperty('--ModeHidingBannerBackgroundColor', '#FFFFFF')
      theme.style.setProperty('--ModeLeftExpandingHover','#EFEFEF')
      theme.style.setProperty('--ModeTopBarBtnHover', '#EFEFEF')
      theme.style.setProperty('--ModeServerListItemBtnColor','#F0F0F0')
    },
    setThemeDark(){
      this.setTheme(themeState.DARK)
      let theme = document.querySelector(':root');
      theme.style.setProperty('--ModeTextColor', 'white');
      theme.style.setProperty('--ModeModalBackgroundColor','#242424')
      theme.style.setProperty('--ModeBtnColor','#5e5e5e')
      theme.style.setProperty('--ModeInputColor','#2f2f2f')
      theme.style.setProperty('--ModeInputInnerTextColor', '#FFFFFF')
      theme.style.setProperty('--ModeLevel1Color','#383838')
      theme.style.setProperty('--ModeLevel2Color','#2C2C2C')
      theme.style.setProperty('--ModeHidingBannerBackgroundColor', '#282828')
      theme.style.setProperty('--ModeLeftExpandingHover','#313131')
      theme.style.setProperty('--ModeTopBarBtnHover', '#545454')
      theme.style.setProperty('--ModeServerListItemBtnColor','#8d8d8d')
    },
    tabClick(index){
      if(index == 1){
        this.tab1Clicked = true
        this.tab2Clicked = false
        this.tab3Clicked = false
      }
      else if(index == 2){
        this.tab1Clicked = false
        this.tab2Clicked = true
        this.tab3Clicked = false
      }
      else if(index == 3){
        this.tab1Clicked = false
        this.tab2Clicked = false
        this.tab3Clicked = true
      }
    },
    checkBoxClick(index){
      if(index == 1){
        this.checkBox2Clicked = false
        this.checkBox3Clicked = false
        this.checkBox1Clicked = true
      }
      else if(index == 2){
        this.checkBox1Clicked = false
        this.checkBox3Clicked = false
        this.checkBox2Clicked = true
        this.setThemeLight()
      }
      else if(index == 3){
        this.checkBox1Clicked = false
        this.checkBox2Clicked = false
        this.checkBox3Clicked = true
        this.setThemeDark()
      }
    }
  }

}
</script>

<style scoped>
.modal{
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.overlay {
  width: 100%;
  height: 100%;
}
.modal-card {
  position: relative;
  max-width: 475px;
  min-height: 100px;
  /*margin: auto;*/
  /*margin-top: 250px;*/
  padding: 20px;
  z-index: 10;
  background-color: #242424;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fade-in-down 0.5s;
}

.modal-card-light{
  position: relative;
  max-width: 475px;
  min-height: 100px;
  /*margin: auto;*/
  /*margin-top: 250px;*/
  padding: 20px;
  z-index: 10;
  background-color: #FFFFFF;
  border-radius: 0.5rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  animation: fade-in-down 0.5s;
}

button {
  background-color: #989898;
  border-radius: 0.2rem;
  border: none;
  box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
  cursor: pointer;
  display: inline-block;
  font-size: 0.8rem;
  color: white;
}

.headerBar{
  display: flex;
  justify-content: space-between;
  height: 3rem;
}

.tabBar{
  display: flex;
  border-bottom: solid 0.001rem #c7c7c728;
  color: white;
  font-size: 0.7rem;
  cursor: pointer;
}

.tabBar-light{
  display: flex;
  border-bottom: solid 0.001rem #c7c7c728;
  color: black;
  font-size: 0.7rem;
  cursor: pointer;
}

.tab{
  margin-right: 10px;
  margin-bottom: 2px;
}

.tab-clicked{
  font-size: 0.75rem;
  border-bottom: solid 2px #4F53AC;
}

.checkboxWrapper{
  display: flex;
  justify-content: space-evenly;
}

.checkbox{
  position: relative;
  margin-top: 10px;
  width: 30%;
  height: auto;
  background-color: #444444;
  text-align: center;
  border-radius: 5px;
  cursor: pointer;
}

.checkbox-clicked{
  margin-top: 10px;
  width: 30%;
  height: auto;
  background-color: #444444;
  text-align: center;
  border-radius: 5px;
  cursor: pointer;
  border: 3px solid #4F53AC;
}

.modeImage{
  margin-top: 5px;
  width: 90%;
}

.modeDesc{
  color: white;
  font-size: 0.8rem;
  margin-bottom: 3px;
}

.settingsNameWrapper {
  justify-content: center;
  color: white;
}

.x{
  width: 12px;
  height: 12px;
  cursor: pointer;
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