import React, { Component } from 'react';
import { Modal, Input, message } from 'antd';
import { ACCESS_TOKEN } from '../../constants';

class GroupJoinModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      code: ''
    };
  }

  handleChange = (e) => {
    this.setState({ code: e.target.value });
  }

  handleSubmit = () => {
    const { code } = this.state;
    const token = localStorage.getItem(ACCESS_TOKEN);

    fetch('/api/groups/join', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ code }),
    })
      .then(res => {
        if (!res.ok) throw new Error('입장 실패');
        return res.json();
      })
      .then(data => {
        message.success(`"${data.name}" 그룹에 입장했어요!`);
        this.props.onJoined(); // 새로고침 콜백
        this.props.onClose();  // 모달 닫기
      })
      .catch(() => {
        message.error('유효하지 않은 코드입니다.');
      });
  }

  render() {
    const { visible, onClose } = this.props;

    return (
      <Modal
        title="참여 코드 입력"
        visible={visible}
        onOk={this.handleSubmit}
        onCancel={onClose}
        okText="입장"
        cancelText="취소"
      >
        <Input
          placeholder="초대 코드 입력 (예: ABC123)"
          value={this.state.code}
          onChange={this.handleChange}
        />
      </Modal>
    );
  }
}

export default GroupJoinModal;
