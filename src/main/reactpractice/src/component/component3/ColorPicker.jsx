import { useState } from "react"

export default function ColorPicker(){
    
    const [currentColor, setCurrentColor] = useState('White') 

    const changeRed = ( ) => {
        
        setCurrentColor (currentColor) = {
            color: 'red',
            
            
        };
    }


    return(<>

    <div style={{color : currentColor}}> 현재 색상 : {currentColor} </div>
    
    <button onClick={chengeRed}> Red </button>
    <button onClick={chengeGreen}> Green </button>
    <button onClick={chengeBlue}> Blue </button>

    </>)
}