
interface TextProps{
  text:string;  
}

export default function Title(props:TextProps) {

  return(
    <h2>{props.text}</h2>
  )

}