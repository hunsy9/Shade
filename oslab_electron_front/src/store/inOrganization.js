export default ({
    namespaced: true,
  
    state: {
        organId: "",
        organName: "",
        projInfos: [
            {
                projName: "MEGASTUDY12",
                servers: [
                    { 
                        serverName: "MEGA_DEV_REDIS", 
                        ip: "123.123.123.123" , 
                        prev: "Develop", 
                        next: "DBMS", 
                    },
                ]
            },
            {
                projName: "MEGASTUDY22",
                servers: [
                    { 
                        serverName: "MEGA_DEV_REDIS", 
                        ip: "123.123.123.123" , 
                        prev: "Develop", 
                        next: "DBMS", 
                    },
                ]
            },
        ]
    },
    getters: {
    },
    mutations: {
        setData(state, organName){
            state.organName = organName;
        }
    },
    actions: {
    },
    modules: {
    },
})