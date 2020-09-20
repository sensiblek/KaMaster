# KaMaster

## ���� ���˻���

 - ������ ������ ����ϴ� ��Ʈ�� ��ȭ�� Inbound ��� ��å�� ����
 - Java 1.8 �̻� ��ġ�� ȯ��

## Master �������� ����

`config/master.properties` ���Ͽ��� `master.port` �� Ÿ �������� ���� ������ ��Ʈ�� ����
������ ���� ���� ��Ʈ �⺻���� `8200`

1���� PC ���� Master, Agent �� ��� �⵿�ϸ鼭 8200�� ��Ʈ�� ����ϴ� ���α׷��� ���ٸ� �������� ���� ���డ��

## ������ ���� ����

java -jar KaMaster.jar

## Agent �������� ����

config/agent.properties ������ ��Ʈ��ȣ�� Master ���� ���� ������ �����Ѵ�.

1���� PC ���� Agent2 �� �⵿�Ϸ���
config/agent2.properties ������ ��Ʈ��ȣ�� Master ���� ���� ������ �����Ѵ�.

## Agent 1 ����

java -jar KaAgent.jar start

## Agent 2 ����

java -jar -Dagent.config=config/agent2.properties KaAgent.jar start


## Agent ���� �� Master ���

1. �ֻ��� ���
    - list : ���� Agent ����� Ȯ��
    - �����Է� : ���� ������ Agent ��ȣ ���� -> ���� �޴� ����
    - exit : Master ���� 
1. ���� ���� Agent �޴�
    - list : Agent ���� ����ҿ� ���� ����� Ȯ��
    - �����Է� : ������ ���� ��ȣ ���� -> ���� �޴� ����
    - x : ���� ��ɾ�� ����
1. ���� ���� �޴�
    - list : ���� ������ Agent ��� Ȯ��
    - �����Է� : ������ ������ Agent ��ȣ ���� -> ���� ����
    - x : ���� ��ɾ�� ����