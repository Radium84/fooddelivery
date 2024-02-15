import "./closeButton.scss";

type CloseButtonType = {
  action: React.Dispatch<React.SetStateAction<boolean>>;
};

function CloseButton({ action }: CloseButtonType) {

  
  return (
    <div className='close' onClick={() => action(false)}>
      <span className='close__line'></span>
    </div>
  );
}

export default CloseButton;
