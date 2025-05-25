import React, { Component } from 'react';
import { Modal, Input, message } from 'antd';
import { ACCESS_TOKEN } from '../../constants';

class GroupCreateModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      description: '',
      imageUrl: ''
    };
  }

  handleChange = (field, value) => {
    this.setState({ [field]: value });
  }

  handleSubmit = () => {
    const { name, description, imageUrl } = this.state;
    const token = localStorage.getItem(ACCESS_TOKEN);

    fetch('/api/groups', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name, description, imageUrl }),
    })
      .then(res => {
        if (!res.ok) throw new Error('생성 실패');
        return res.json();
      })
      .then(data => {
        message.success(`그룹 "${data.name}" 생성 완료`);
        this.props.onCreated(); // 부모 콜백 실행
        this.props.onClose();   // 모달 닫기
      })
      .catch(() => {
        message.error('그룹 생성에 실패했습니다.');
      });
  }

  render() {
    const { visible, onClose } = this.props;
    const { name, description, imageUrl } = this.state;

    return (
      <Modal
        title="새 그룹 만들기"
        visible={visible}
        onOk={this.handleSubmit}
        onCancel={onClose}
        okText="생성"
        cancelText="취소"
      >
        <Input
          placeholder="그룹 이름"
          value={name}
          onChange={e => this.handleChange('name', e.target.value)}
          style={{ marginBottom: 12 }}
        />
        <Input.TextArea
          placeholder="그룹 소개"
          value={description}
          onChange={e => this.handleChange('description', e.target.value)}
          rows={3}
          style={{ marginBottom: 12 }}
        />
        <Input
          placeholder="이미지 URL (선택)"
          value={imageUrl}
          onChange={e => this.handleChange('imageUrl', e.target.value)}
        />
      </Modal>
    );
  }
}

export default GroupCreateModal;
