import "./button.scss";
type TButtonProps = {
  classNames: string;
  type: "submit" | "reset" | "button";
  text: string;
  action: (e?: React.SyntheticEvent) => void;
};
function Button({ classNames, type, text, action }: TButtonProps) {
  return (
    <button className={`btn ${classNames}`} type={type} onClick={action}>
      {text}
    </button>
  );
}

export default Button;
